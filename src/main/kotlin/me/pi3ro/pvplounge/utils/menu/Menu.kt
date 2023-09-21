package me.pi3ro.pvplounge.utils.menu

import me.pi3ro.pvplounge.PvPLounge.Companion.getInstance
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil

abstract class Menu {
    protected var plugin = getInstance()
    var buttons: MutableMap<Int, Button?> = HashMap()
    private val autoUpdate = false
    private val updateAfterClick = true
    public var closedByMenu = false
    private val placeholder = false
    private val placeholderButton = Button.placeholder(Material.STAINED_GLASS_PANE, 15.toByte(), " ")
    private fun createItemStack(player: Player, button: Button?): ItemStack {
        val item = button!!.getButtonItem(player)
        if (item.type != Material.SKULL_ITEM) {
            val meta = item.itemMeta
            if (meta != null && meta.hasDisplayName()) {
                meta.displayName = meta.displayName + "§b§c§d§e"
            }
            item.setItemMeta(meta)
        }
        return item
    }

    fun openMenu(player: Player) {
        buttons = getButtons(player)
        val previousMenu = currentlyOpenedMenus[player.name]
        var inventory: Inventory? = null
        val size = if (size == -1) size(buttons) else size
        var update = false
        var title = translate(getTitle(player))
        if (title.length > 32) {
            title = title.substring(0, 32)
        }
        if (player.openInventory != null) {
            if (previousMenu == null) {
                player.closeInventory()
            } else {
                val previousSize = player.openInventory.topInventory.size
                if (previousSize == size && player.openInventory.topInventory.title == title) {
                    inventory = player.openInventory.topInventory
                    update = true
                } else {
                    previousMenu.closedByMenu = true
                    player.closeInventory()
                }
            }
        }
        if (inventory == null) {
            inventory = Bukkit.createInventory(player, size, title)
        }
        inventory!!.contents = arrayOfNulls(inventory.size)
        currentlyOpenedMenus[player.name] = this
        for ((key, value) in buttons) {
            inventory.setItem(key, createItemStack(player, value))
        }
        if (this.placeholder) {
            for (index in 0 until size) {
                if (buttons[index] == null) {
                    buttons[index] = placeholderButton
                    inventory.setItem(index, placeholderButton.getButtonItem(player))
                }
            }
        }
        if (update) {
            player.updateInventory()
        } else {
            player.openInventory(inventory)
        }
        onOpen(player)
        this.closedByMenu = false
    }

    fun size(buttons: Map<Int, Button?>): Int {
        var highest = 0
        for (buttonValue in buttons.keys) {
            if (buttonValue > highest) {
                highest = buttonValue
            }
        }
        return (ceil((highest + 1) / 9.0) * 9.0).toInt()
    }

    val size: Int
        get() = -1

    abstract fun getTitle(player: Player?): String?
    abstract fun getButtons(player: Player?): MutableMap<Int, Button?>
    fun onOpen(player: Player?) {}
    open fun onClose(player: Player?) {}
    fun translate(msg: String?): String {
        return ChatColor.translateAlternateColorCodes('&', msg)
    }

    companion object {
        var currentlyOpenedMenus: MutableMap<String, Menu> = HashMap()
    }
}
