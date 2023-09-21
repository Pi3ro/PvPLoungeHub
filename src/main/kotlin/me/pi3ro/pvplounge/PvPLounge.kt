package me.pi3ro.pvplounge

import me.pi3ro.pvplounge.listeners.PlayerListener
import me.pi3ro.pvplounge.scoreboard.ScoreboardProvider
import me.pi3ro.pvplounge.utils.Utils
import me.pi3ro.pvplounge.utils.menu.MenuListener
import io.github.thatkawaiisam.assemble.Assemble
import io.github.thatkawaiisam.assemble.AssembleStyle
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by: Pi3ro
 * Project: PvPLoungeHub
 * Date: 2023-09-14 @ 05:01
 **/
class PvPLounge : JavaPlugin() {

    override fun onEnable() {
        instance = this
        val config = File(dataFolder, "config.yml")
        if (!config.exists()){
            getConfig().options().copyDefaults(true)
            saveConfig()
        }
        init()
        val time = System.currentTimeMillis()
        logger("&m------------------------------------------------")
        logger("          &6&lPvPLounge &7[Hub]")
        logger(" ")
        logger(" &7| &6Version: &f" + description.version)
        logger(" &7| &6Author: &f" + description.authors)
        logger(" &7| &6Initialized in &c" + (System.currentTimeMillis() - time) + " &6ms")
        logger(" ")
        logger("&m------------------------------------------------")
    }

    fun init() {
        for (world in Bukkit.getWorlds()) {
            world.setGameRuleValue("doDaylightCycle", "false")
            world.setGameRuleValue("doMobSpawning", "false")
            world.time = 0
            world.setStorm(false)
        }
        if (this.config.getBoolean("SCOREBOARD.ENABLED")) {
            val assemble = Assemble(this, ScoreboardProvider())
            assemble.ticks = 2
            assemble.assembleStyle = AssembleStyle.VIPER
        }
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)
    }

    fun logger(msg: String?) { Bukkit.getConsoleSender().sendMessage(Utils.translate(msg)) }

    companion object {
        private lateinit var instance: PvPLounge
        @JvmStatic
        fun getInstance(): PvPLounge {
            return instance
        }
    }
}