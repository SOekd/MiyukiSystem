package miyukisystem.util

import miyukisystem.Main
import org.bukkit.Bukkit

class AsyncUtil {

     companion object {

         fun runAsync(run: Runnable) {
             Bukkit.getScheduler().runTaskAsynchronously(Main.instance, run)
         }

         fun runAsyncLater(delay: Long, run: Runnable) {
             Bukkit.getScheduler().runTaskLaterAsynchronously(Main.instance, run, delay)
         }

         fun runAsyncTimer(delay: Long, period: Long, run: Runnable) {
             Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, run, delay, period)
         }

         fun runSync(run: Runnable) {
             Bukkit.getScheduler().runTask(Main.instance, run)
         }

         fun runSyncLater(delay: Long, run: Runnable) {
             Bukkit.getScheduler().runTaskLater(Main.instance, run, delay)
         }

         fun runSyncTimer(delay: Long, period: Long, run: Runnable) {
             Bukkit.getScheduler().runTaskTimer(Main.instance, run, delay, period)
         }
     }

}

fun Any.sync(run: () -> Unit) {
    Bukkit.getScheduler().runTask(Main.instance, run)
}

fun Any.syncLater(delay: Long, run: () -> Unit) {
    Bukkit.getScheduler().runTaskLater(Main.instance, run, delay)
}

fun Any.syncTimer(delay: Long, period: Long, run: () -> Unit) {
    Bukkit.getScheduler().runTaskTimer(Main.instance, run, delay, period)
}

fun Any.async(run: () -> Unit) {
    Bukkit.getScheduler().runTaskAsynchronously(Main.instance, run)
}

fun Any.asyncLater(delay: Long, run: () -> Unit) {
    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.instance, run, delay)
}

fun Any.asyncTimer(delay: Long, period: Long, run: () -> Unit) {
    Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, run, delay, period)
}
