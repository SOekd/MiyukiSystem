package miyukisystem.commands.impl.subcommands;

import miyukisystem.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Exemple extends SubCommand {

    public Exemple() {
        super("Exemplo");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String command) {
        return false;
    }
}
