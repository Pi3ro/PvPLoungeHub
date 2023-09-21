package me.pi3ro.pvplounge.utils

import me.pi3ro.pvplounge.PvPLounge
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

/**
 * Created by: Pi3ro
 * Project: PvPLoungeHub
 * Date: 2023-09-14 @ 07:43
 **/
object Utils {
    fun sendToServer(player: Player, server: String) {
        val b = ByteArrayOutputStream()
        val out = DataOutputStream(b)

        try {
            out.writeUTF("Connect")
            out.writeUTF(server)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        player.sendPluginMessage(PvPLounge.getInstance(), "BungeeCord", b.toByteArray())
    }

    fun translate(msg: String?): String{ return ChatColor.translateAlternateColorCodes('&', msg) }
}