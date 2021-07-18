package miyukisystem.commands.impl.subcommands;

import lombok.val;
import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import miyukisystem.model.WhitelistPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhitelistAddSubCommand extends SubCommand {

    public WhitelistAddSubCommand() {
        super("WhitelistAdd");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        if(args.length < 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectWhitelistAddSubCommand");
            return false;
        }

        val target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
            return false;
        }

        WhitelistPlayer whitelistPlayer = new WhitelistPlayer("target", false, false, false);
        whitelistPlayer.save();
        WhitelistManager.Companion.set(whitelistPlayer);
        MessageManagerKt.sendCustomMessage(sender, "AddedPlayerOnWhitelist");

        return true;
    }
}
