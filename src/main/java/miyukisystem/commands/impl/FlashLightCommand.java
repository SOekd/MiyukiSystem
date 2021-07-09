package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class FlashLightCommand extends CommandService {

    public FlashLightCommand() {
        super("FlashLight", "miyukisystem.flaslight");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }


        if (args.length > 0) {
            sender.sendMessage("IncorrectFlashLightCommand");
            return false;
        }

        Player player = (Player) sender;

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) { // adicionar verificacao de amplifier e tempo
            if(player.getPotionEffect(PotionEffectType.NIGHT_VISION).getDuration() < 480) { // a poção não é nula, temos a verificação acima
                player.sendMessage("OnNightVisionPotion");
            } else {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage("LuzDisabled");
            }
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 50), true);
            player.sendMessage("LuzActivated");
        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
