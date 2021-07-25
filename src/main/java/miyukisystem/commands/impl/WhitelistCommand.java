package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.commands.SubCommand;
import miyukisystem.commands.impl.subcommands.whitelist.*;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.whitelist")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        if (args.length == 1) {
            return subCommands.stream()
                    .map(it -> it.getAliases().stream().findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(it -> StringUtils.startsWithIgnoreCase(it, lastWord))
                    .sorted()
                    .collect(Collectors.toList());
        }
        else if (args.length == 3) {
            List<String> aliases = new ArrayList<>();
            subCommands.forEach(it -> {
                if (it instanceof WhitelistPermissionSubCommand) {
                    ((WhitelistPermissionSubCommand) it).subCommands.stream()
                            .map(subCommand -> subCommand.getAliases().stream().findFirst())
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(aliases::add);
                }
            });
            return aliases;
        } else {
            return Bukkit.getOnlinePlayers().stream()
                    .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                    .map(HumanEntity::getName)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

}
