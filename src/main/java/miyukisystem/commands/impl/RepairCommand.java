package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.util.NBTEditor;
import miyukisystem.util.XMaterial;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class RepairCommand extends CommandService {

    public RepairCommand() {
        super("Repair", "miyukisystem.repair", false);
    }

    // Em desenvolvimento, pode ocorrer vários bugs.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;
        val itemInHand = player.getInventory().getItemInHand();

        if(XMaterial.matchXMaterial(itemInHand.getType()).parseMaterial() == XMaterial.AIR.parseMaterial() || itemInHand.getType().getMaxDurability() == 0) {
            MessageManagerKt.sendCustomMessage(player, "NoReparable");
            return false;
        }

        if(itemInHand.getDurability() == 0) {
            MessageManagerKt.sendCustomMessage(player, "MaxDurability");
            return false;
        }

        if(ConfigManager.Companion.getConfig().config.getStringList("RepairBlockedNBTs").stream().anyMatch(it -> NBTEditor.contains(itemInHand, it))) {
            MessageManagerKt.sendCustomMessage(player, "BlockedRepairItem");
            return false;
        }

        itemInHand.setDurability((short) 0);
        MessageManagerKt.sendCustomMessage(player, "SuccessRepair");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
