package me.pi3ro.pvplounge.listeners

import me.pi3ro.pvplounge.PvPLounge
import me.pi3ro.pvplounge.menu.ServerSelectorMenu
import me.pi3ro.pvplounge.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.weather.WeatherChangeEvent

/**
 * Created by: Pi3ro
 * Project: PvPLoungeHub
 * Date: 2023-09-14 @ 05:04
 **/
class PlayerListener : Listener {
    private val config = PvPLounge.getInstance().config

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = null
        val player = event.player
        for (toHide in Bukkit.getServer().onlinePlayers) {
            for (player in Bukkit.getServer().onlinePlayers) {
                if (player != toHide) {
                    player.hidePlayer(toHide)
                }
            }
        }
        player.teleport(Location(player.world, 0.0, 245.0, 0.0))
        player.inventory.clear()
        player.allowFlight = true
        player.isFlying = true
        player.flySpeed = 0F
        if (config.getBoolean("JOIN-MESSAGE.ENABLED")) {
            config.getStringList("JOIN-MESSAGE.LINES")?.forEach { message ->
                player.sendMessage(Utils.translate(message))
            }
        }
        Bukkit.getScheduler().runTaskLater(PvPLounge.getInstance(), Runnable {
            ServerSelectorMenu().openMenu(player)
        }, 5L)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        event.quitMessage = null
        player.inventory.clear()
    }

    @EventHandler
    fun onChangeFood(event: FoodLevelChangeEvent){
        event.isCancelled = true
    }

    @EventHandler
    fun onWeather(event: WeatherChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onMobSpawn(event: CreatureSpawnEvent) {
        event.isCancelled = true
    }
}
