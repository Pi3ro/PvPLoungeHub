package me.pi3ro.pvplounge.utils.menu

import org.apache.commons.lang.StringUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

abstract class Button {
    abstract fun getButtonItem(player: Player?): ItemStack
    open fun clicked(player: Player?, clickType: ClickType?) {}
    fun clicked(player: Player?, slot: Int, clickType: ClickType?, hotbarSlot: Int) {}
    open fun shouldCancel(player: Player?, clickType: ClickType?): Boolean {
        return true
    }

    fun shouldUpdate(player: Player?, clickType: ClickType?): Boolean {
        return false
    }

    companion object {
        fun placeholder(material: Material?, data: Byte, vararg title: String?): Button {
            return object : Button() {
                override fun getButtonItem(player: Player?): ItemStack {
                    val it = ItemStack(material, 1, data.toShort())
                    val meta = it.itemMeta
                    meta.displayName = StringUtils.join(title)
                    it.setItemMeta(meta)
                    return it
                }
            }
        }
    }
}