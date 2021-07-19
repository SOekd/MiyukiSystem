package miyukisystem.commands.impl.subcommands.whitelist;

import miyukisystem.commands.SubCommand;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WhitelistReloadSubCommand extends SubCommand {

    public WhitelistReloadSubCommand() {
        super("WhitelistReload");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {

        WhitelistManager.Companion.reload();
        MessageManagerKt.sendCustomMessage(sender, "WhitelistReloaded");

        return true;
    }
}
