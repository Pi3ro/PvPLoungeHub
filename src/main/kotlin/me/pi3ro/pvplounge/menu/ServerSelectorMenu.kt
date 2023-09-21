package me.pi3ro.pvplounge.menu

import me.pi3ro.pvplounge.PvPLounge
import me.pi3ro.pvplounge.utils.ItemBuilder
import me.pi3ro.pvplounge.utils.Utils
import me.pi3ro.pvplounge.utils.menu.Button
import me.pi3ro.pvplounge.utils.menu.Menu
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

/**
 * Created by: Pi3ro
 * Project: PvPLoungeHub
 * Date: 2023-09-14 @ 05:04
 **/
class ServerSelectorMenu : Menu() {
    override fun getTitle(player: Player?): String? {
        return Utils.translate(PvPLounge.getInstance().config.getString("SERVERS.TITLE"))
    }

    override fun getButtons(player: Player?): MutableMap<Int, Button?> {
        val buttons: MutableMap<Int, Button?> = HashMap()

        for (i in 1..9) {
            val config = PvPLounge.getInstance().config.getConfigurationSection("SERVERS.SERVER_$i")
            if (config != null) {
                val serverButton = ServerButton(i)
                val slot = config.getInt("SLOT")
                buttons[slot] = serverButton
            }
        }

        val leaveButton = LeaveButton()
        val leaveSlot = PvPLounge.getInstance().config.getInt("SERVERS.LEAVE.SLOT")
        buttons[leaveSlot] = leaveButton

        return buttons
    }

    inner class ServerButton(private val serverNumber: Int) : Button() {
        override fun getButtonItem(viewer: Player?): ItemStack {
            val serverConfig = PvPLounge.getInstance().config.getConfigurationSection("SERVERS.SERVER_$serverNumber")
            val loreList = serverConfig.getStringList("LORE").map { PlaceholderAPI.setPlaceholders(viewer, it) }
            return ItemBuilder(Material.getMaterial(serverConfig.getString("ITEM")))
                .setDisplayName(serverConfig.getString("TITLE"))
                .setDurability(serverConfig.getInt("DURABILITY"))
                .setLore(loreList)
                .clearFlags()
                .build()
        }

        override fun clicked(player: Player?, clickType: ClickType?) {
            Utils.sendToServer(player!!,
                PvPLounge.getInstance().config.getString("SERVERS.SERVER_$serverNumber.SEND-SERVER"))
        }
    }

    inner class LeaveButton : Button() {
        override fun getButtonItem(viewer: Player?): ItemStack {
            val leaveConfig = PvPLounge.getInstance().config.getConfigurationSection("SERVERS.LEAVE")
            return ItemBuilder(Material.getMaterial(leaveConfig.getString("ITEM")))
                .setDisplayName(leaveConfig.getString("TITLE"))
                .setDurability(leaveConfig.getInt("DURABILITY"))
                .setLore(leaveConfig.getStringList("LORE"))
                .clearFlags()
                .build()
        }

        override fun clicked(player: Player?, clickType: ClickType?) {
            player!!.kickPlayer(
                Utils.translate(
                PvPLounge.getInstance().config.getString("SERVERS.LEAVE.MESSAGE")
            ))
        }
    }

    override fun onClose(player: Player?) {
        Bukkit.getScheduler().runTaskLater(PvPLounge.getInstance(), Runnable {
            openMenu(player!!)
        }, 5L)
    }
}
