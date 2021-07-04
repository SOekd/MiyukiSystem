package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class LuzCommand extends CommandService {

    public LuzCommand() {
        super("Luz");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.luz"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        if (args.length > 0) {
            sender.sendMessage("IncorrectLuzCommand");
            return false;
        }

        Player player = (Player) sender;

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) { // adicionar verificacao de amplifier e tempo
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage("LuzDisabled");
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 50), true);
            player.sendMessage("LuzActivated");
        }

        return false;
    }
}
