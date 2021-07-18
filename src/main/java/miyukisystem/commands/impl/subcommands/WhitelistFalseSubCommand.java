package miyukisystem.commands.impl.subcommands;

import lombok.val;
import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhitelistFalseSubCommand extends SubCommand {

    public WhitelistFalseSubCommand() {
        super("WhitelistFalse");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        val manager = WhitelistManager.Companion;

        if(!manager.getState()) {
            MessageManagerKt.sendCustomMessage(sender, "WhitelistAlreadyFalse");
            return false;
        }

        manager.setState(false);
        MessageManagerKt.sendCustomMessage(sender, "WhitelistFalse");

        return true;
    }
}
