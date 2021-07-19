package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import net.wesjd.anvilgui.version.VersionMatcher;
import net.wesjd.anvilgui.version.VersionWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AnvilCommand extends CommandService {

    public AnvilCommand() {
        super("Anvil", "miyukisystem.anvil", false);
    }

    private final VersionWrapper WRAPPER = new VersionMatcher().match();

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }
        val player = (Player) sender;

        player.closeInventory();

        WRAPPER.handleInventoryCloseEvent(player);
        WRAPPER.setActiveContainerDefault(player);

        val title = " ";
        final Object container = WRAPPER.newContainerAnvil(player, title);

        val containerId = WRAPPER.getNextContainerId(player, container);
        WRAPPER.sendPacketOpenWindow(player, containerId, title);
        WRAPPER.setActiveContainer(player, container);
        WRAPPER.setActiveContainerId(container, containerId);
        WRAPPER.addActiveContainerSlotListener(container, player);
        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }


}
