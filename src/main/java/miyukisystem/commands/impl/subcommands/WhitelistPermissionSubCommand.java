package miyukisystem.commands.impl.subcommands;

import lombok.val;
import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhitelistPermissionSubCommand extends SubCommand {

    public WhitelistPermissionSubCommand() {
        super("WhitelistPermission");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        if(args.length < 3) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectWhitelistPermissionSubCommand");
            return false;
        }

        val manager = WhitelistManager.Companion;

        if(!manager.has(args[0])) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerNotOnTheWhitelist");
            return false;
        }

        val target = manager.get(args[0]);

        switch (args[1]) {
            case "break":
            case "breakblocks":
            case "quebrar":
            case "quebrarblocos":
                if(target.getCanBreak()) {
                    target.setCanBreak(false);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanBreakFalse");
                } else {
                    target.setCanBreak(true);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanBreakTrue");
                }
                break;
            case "comandos":
            case "executarcomandos":
            case "executecommands":
            case "commands":
                if(target.getCanExecuteCommands()) {
                    target.setCanExecuteCommands(false);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionExecuteCommandsFalse");
                } else {
                    target.setCanExecuteCommands(true);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionExecuteCommandsTrue");
                }
                break;
            case "placeblocks":
            case "colocarblocos":
            case "colocar":
            case "place":
                if(target.getCanPlace()) {
                    target.setCanPlace(false);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanPlaceFalse");
                } else {
                    target.setCanPlace(true);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanPlaceTrue");
                }
                break;
        }
        target.save();

        return true;
    }
}
