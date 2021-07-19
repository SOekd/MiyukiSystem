package miyukisystem.commands.impl.subcommands.whitelist;

import lombok.val;
import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhitelistPermissionSubCommand extends SubCommand {

    public List<SubCommand> subCommands = new ArrayList<>();

    public WhitelistPermissionSubCommand() {
        super("WhitelistPermission");

        subCommands.add(new SubCommand("WhitelistPermissionBreakBlock") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

                val target = WhitelistManager.Companion.get(args[0]);

                if(target.getCanBreak()) {
                    target.setCanBreak(false);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanBreakFalse");
                } else {
                    target.setCanBreak(true);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanBreakTrue");
                }
                return true;
            }
        });

        subCommands.add(new SubCommand("WhitelistPermissionExecuteCommands") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

                val target = WhitelistManager.Companion.get(args[0]);

                if(target.getCanExecuteCommands()) {
                    target.setCanExecuteCommands(false);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionExecuteCommandsFalse");
                } else {
                    target.setCanExecuteCommands(true);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionExecuteCommandsTrue");
                }
                return true;
            }
        });

        subCommands.add(new SubCommand("WhitelistPermissionPlaceBlocks") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

                val target = WhitelistManager.Companion.get(args[0]);

                if(target.getCanPlace()) {
                    target.setCanPlace(false);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanPlaceFalse");
                } else {
                    target.setCanPlace(true);
                    MessageManagerKt.sendCustomMessage(sender, "WhitelistPermissionCanPlaceTrue");
                }
                return true;
            }
        });

    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        if(args.length != 2) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectWhitelistPermissionSubCommand");
            return false;
        }

        if(!WhitelistManager.Companion.has(args[0])) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerNotOnTheWhitelist");
            return false;
        }

        for (SubCommand subCommand : subCommands) {

            if (subCommand.getAliases().contains(args[1].toLowerCase())) {
                val arg = new String[] { "", args[0], args[1] };
                return subCommand.execute(sender, arg);
            }

        }

        // manda o help aq kroxy

        return true;
    }
}
