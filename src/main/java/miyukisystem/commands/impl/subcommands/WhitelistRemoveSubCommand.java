package miyukisystem.commands.impl.subcommands;

import lombok.val;
import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhitelistRemoveSubCommand extends SubCommand {

    public WhitelistRemoveSubCommand() {
        super("WhitelistRemove");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        if(args.length < 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectWhitelistRemoveSubCommand");
            return false;
        }

        if(!WhitelistManager.Companion.has(args[0])) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerNotOnTheWhitelist");
            return false;
        }

        val target = WhitelistManager.Companion.get(args[0]);
        val targetName = target.getPlayerName();

        WhitelistManager.Companion.remove(targetName);
        MessageManagerKt.sendCustomMessage(sender, "RemovedThePlayerFromTheWhitelist");

        return true;
    }
}
