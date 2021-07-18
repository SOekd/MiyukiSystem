package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.commands.SubCommand;
import miyukisystem.commands.impl.subcommands.*;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhitelistCommand extends CommandService {

    List<SubCommand> subCommands = new ArrayList<>();

    public WhitelistCommand() {
        super("Whitelist", "miyukisystem.whitelist", false);
        subCommands.add(new WhitelistAddSubCommand());
        subCommands.add(new WhitelistRemoveSubCommand());
        subCommands.add(new WhitelistPermissionSubCommand());
        subCommands.add(new WhitelistReloadSubCommand());
        subCommands.add(new WhitelistTrueSubCommand());
        subCommands.add(new WhitelistFalseSubCommand());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if(args.length > 0) {
            for(SubCommand subCommand : subCommands) {
                List<String> aliases = subCommand.getAliases();
                if(aliases.contains(args[0].toLowerCase())) {
                    return subCommand.execute(sender, args);
                }
            }
        }

        MessageManagerKt.sendCustomMessage(sender, "WhitelistHelp");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
