package miyukisystem.commands.impl;

import com.cryptomorin.xseries.XSound;
import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ExecuteSoundCommand extends CommandService {

    public ExecuteSoundCommand() {
        super("ExecuteSound", "miyukisystem.executesound", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length != 2) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectExecuteSoundCommand");
            return false;
        }

        val target = Bukkit.getPlayer(args[0]);
        val sound = XSound.matchXSound(args[1].toUpperCase());

        if (!sound.isPresent()) {
            MessageManagerKt.sendCustomMessage(sender, "SoundNotFound");
            return false;
        }

        if (target == null) {

            if (args[0].equalsIgnoreCase("*")) {

                Bukkit.getOnlinePlayers().forEach(
                        it -> it.playSound(it.getLocation(), sound.get().parseSound(), 1.0f, 1.0f)
                );

                MessageManagerKt.sendCustomMessage(sender, "SoundSent");
                return false;
            }

            MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
            return false;
        }

        target.playSound(target.getLocation(), sound.get().parseSound(), 1.0f, 1.0f);

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{player}", target.getName());

        MessageManagerKt.sendCustomMessage(sender, "SoundSentTarget", placeHolders);

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.executesound")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        if (lastWord.length() <= 0) return Collections.emptyList();
        return Arrays.stream(Sound.values())
                .map(Sound::name)
                .filter(it -> StringUtils.startsWithIgnoreCase(it, lastWord))
                .sorted()
                .collect(Collectors.toList());
    }

}
