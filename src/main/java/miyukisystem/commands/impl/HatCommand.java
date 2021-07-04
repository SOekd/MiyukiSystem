package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class HatCommand extends CommandService {

    public HatCommand() {
        super("hat");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.hat"))) {
            sender.sendMessage("NoPerm");
            return false;
        }

        Player player = (Player) sender;
        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand.getType().name().contains("HELMET") || itemInHand.getType() != Material.AIR && itemInHand.getType().getMaxDurability() == 0) {
            ItemStack helmet = playerInventory.getHelmet();
            playerInventory.setHelmet(itemInHand);
            player.setItemInHand(helmet);
            player.sendMessage("HatPlaced");
        } else {
            player.sendMessage("InvalidHat");
        }

        return false;
    }
}
