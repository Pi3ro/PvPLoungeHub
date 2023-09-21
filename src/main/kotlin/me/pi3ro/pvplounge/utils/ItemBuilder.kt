package me.pi3ro.pvplounge.utils

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

/**
 * Created by: Pi3ro
 * Project: PvPLoungeHub
 * Date: 2023-09-14 @ 06:29
 **/
class ItemBuilder {

    private val itemStack: ItemStack

    constructor(material: Material) {
        itemStack = ItemStack(material)
    }

    fun setDisplayName(string: String): ItemBuilder {
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName = Utils.translate(string)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun setDurability(int: Int): ItemBuilder {
        itemStack.durability = int.toShort()
        return this
    }

    fun setLore(list: List<String>): ItemBuilder {
        if (list.isEmpty()) {
            return this
        }
        val itemMeta: ItemMeta = itemStack.itemMeta
        val modifiedList = mutableListOf<String>()
        for (i in list.indices) {
            val string: String = list[i].replace("&", "§")
            modifiedList.add(string)
        }
        itemMeta.lore = modifiedList
        itemStack.itemMeta = itemMeta
        return this
    }

    fun clearFlags(): ItemBuilder {
        val itemMeta = itemStack.itemMeta
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_PLACED_ON)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun build(): ItemStack {
        return this.itemStack
    }
}