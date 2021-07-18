package miyukisystem.commands.impl.subcommands;

import lombok.val;
import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhitelistTrueSubCommand extends SubCommand {

    public WhitelistTrueSubCommand() {
        super("WhitelistTrue");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        val manager = WhitelistManager.Companion;

        if(manager.getState()) {
            MessageManagerKt.sendCustomMessage(sender, "WhitelistAlreadyTrue");
            return false;
        }

        manager.setState(true);
        MessageManagerKt.sendCustomMessage(sender, "WhitelistTrue");

        return true;
    }
}
