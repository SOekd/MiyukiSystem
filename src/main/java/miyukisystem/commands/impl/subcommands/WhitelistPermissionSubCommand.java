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
            MessageManagerKt.sendCustomMessage(sender, "IncorrectWhitelistRemoveSubCommand");
            return false;
        }

        val manager = WhitelistManager.Companion;

        if(!manager.has(args[0])) {
            MessageManagerKt.sendCustomMessage(sender, "IsNotInWhitelist");
            return false;
        }

        val target = manager.get(args[0]);
        val targetName = target.getPlayerName();

        switch (args[1]) {
            case "break":
            case "breakblocks":
            case "quebrar":
            case "quebrarblocos":
                if(target.getCanBreak()) {
                    target.setCanBreak(false);
                    // mensagem que adicionou como false o break
                } else {
                    target.setCanBreak(true);
                }
                break;
            case "comandos":
            case "executarcomandos":
            case "executecommands":
            case "commands":
                if(target.getCanExecuteCommands()) {
                    target.setCanExecuteCommands(false);
                    // mensagem que adicionou como false o executecommands
                } else {
                    target.setCanExecuteCommands(true);
                }
                break;
            case "placeblocks":
            case "colocarblocos":
            case "colocar":
            case "place":
                if(target.getCanPlace()) {
                    target.setCanPlace(false);
                    // mensagem que adicionou como false o place
                } else {
                    target.setCanPlace(true);
                }
                break;
        }
        target.save();

        return true;
    }
}
