package miyukisystem.hook

import br.com.devpaulo.legendchat.Main.econ
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit.getServer


class Vault {

    companion object {

        lateinit var economy: Economy

        fun setupEconomy(): Boolean {
            if (getServer().pluginManager.getPlugin("Vault") == null)
                return false
            val rsp = getServer().servicesManager.getRegistration(Economy::class.java) ?: return false
            econ = rsp.provider
            return econ != null
        }

    }

}