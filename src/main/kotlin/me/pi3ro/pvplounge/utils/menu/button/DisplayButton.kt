package me.pi3ro.pvplounge.utils.menu.button

import me.pi3ro.pvplounge.utils.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class DisplayButton : Button() {
    private val itemStack: ItemStack? = null
    private val cancel = false
    override fun getButtonItem(player: Player?): ItemStack {
        return itemStack ?: ItemStack(Material.AIR)
    }

    override fun shouldCancel(player: Player?, clickType: ClickType?): Boolean {
        return cancel
    }
}
