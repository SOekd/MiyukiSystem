package miyukisystem.commands.impl;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class EnchantCommand extends CommandService {

    public EnchantCommand() {
        super("Enchant", "miyukisystem.enchant", false);
    }

    @SuppressWarnings("all")
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length != 2) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectEnchantCommand");
        }

        val player = (Player) sender;
        val itemInHand = player.getItemInHand();

        if (XMaterial.matchXMaterial(itemInHand.getType()).parseMaterial() == XMaterial.AIR.parseMaterial()) {
            MessageManagerKt.sendCustomMessage(sender, "PutAItemInHand");
            return false;
        }

        val enchant = XEnchantment.matchXEnchantment(args[0].toUpperCase());


        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
