package miyukisystem.commands.impl;

import com.cryptomorin.xseries.XMaterial;
import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.util.NBTUtilKt;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RepairCommand extends CommandService {

    public RepairCommand() {
        super("Repair", "miyukisystem.repair", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        val config = ConfigManager.Companion.getConfig().config;

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;
        val itemInHand = player.getInventory().getItemInHand();

        if (XMaterial.matchXMaterial(itemInHand.getType()).parseMaterial() == XMaterial.AIR.parseMaterial() || itemInHand.getType().getMaxDurability() == 0) {
            MessageManagerKt.sendCustomMessage(player, "NoReparable");
            return false;
        }

        if (itemInHand.getDurability() == 0) {
            MessageManagerKt.sendCustomMessage(player, "MaxDurability");
            return false;
        }

        if (config.getStringList("RepairBlockedNBTs").stream().anyMatch(it -> NBTUtilKt.containsNBT(itemInHand, it))) {
            MessageManagerKt.sendCustomMessage(player, "BlockedRepairItem");
            return false;
        }

        val items = config.getStringList("RepairBlockedItems").stream().map(XMaterial::matchXMaterial).filter(Optional::isPresent).map(Optional::get);
        val hand = XMaterial.matchXMaterial(itemInHand);
        if (items.anyMatch(it -> it == hand)) {
            MessageManagerKt.sendCustomMessage(player, "BlockedRepairItem");
            return false;
        }
        itemInHand.setDurability((short) 0);
        MessageManagerKt.sendCustomMessage(player, "SuccessRepair");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
