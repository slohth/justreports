package dev.slohth.justreports.utils.framework.menu

import dev.slohth.justreports.utils.CC.trns
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.inventory.Inventory
import java.util.HashMap
import dev.slohth.justreports.utils.framework.menu.ShapedMenuPattern
import org.bukkit.event.inventory.InventoryType
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryCloseEvent
import java.lang.Runnable
import org.bukkit.event.inventory.InventoryClickEvent
import dev.slohth.justreports.utils.CC
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

abstract class Menu(private val core: JavaPlugin, title: String?, rows: Int) : Listener {
    private var inventory: Inventory
    private val rows: Int
    private val title: String
    private val items: MutableMap<Int, ItemStack> = HashMap()
    private val buttons: MutableMap<Int, Button> = HashMap()

    fun applyMenuPattern(pattern: ShapedMenuPattern) {
        items.putAll(pattern.getItems())
        buttons.putAll(pattern.getButtons())
    }

    fun firstEmpty(): Int {
        for (i in 0 until rows * 9) {
            if (items.containsKey(i) || buttons.containsKey(i)) continue
            return i
        }
        return rows * 9
    }

    fun setInventoryType(type: InventoryType?) {
        inventory = Bukkit.createInventory(null, type!!, title)
    }

    fun setItem(slot: Int, item: ItemStack) {
        items[slot] = item
    }

    fun getItem(slot: Int): ItemStack? {
        return inventory.getItem(slot)
    }

    fun setButton(slot: Int, button: Button) {
        buttons[slot] = button
    }

    fun build(): Menu {
        if (!items.isEmpty()) for (i in items.keys) inventory.setItem(i, items[i])
        if (!buttons.isEmpty()) for (i in buttons.keys) inventory.setItem(i, buttons[i]!!.icon)
        return this
    }

    fun open(player: Player) {
        player.openInventory(inventory)
    }

    fun update(player: Player) {
        player.closeInventory()
        open(player)
    }

    fun updateAll() {
        for (e in inventory.viewers) {
            e.closeInventory()
            open(e as Player)
        }
    }

    fun close() {
        InventoryCloseEvent.getHandlerList().unregister(this)
        items.clear()
        buttons.clear()
        Bukkit.getScheduler()
            .runTaskLater(core, Runnable { for (e in ArrayList(inventory.viewers)) e.closeInventory() }, 1)
    }

    @EventHandler
    fun onInteract(e: InventoryClickEvent) {
        if (!inventory.viewers.contains(e.whoClicked) || e.whoClicked !is Player) return
        e.isCancelled = true
        if (buttons.containsKey(e.slot)) buttons[e.slot]!!.clicked(e.whoClicked as Player)
    }

    init {
        this.title = trns(title!!)
        this.rows = rows
        inventory = Bukkit.createInventory(null, rows * 9, this.title)
        Bukkit.getPluginManager().registerEvents(this, core)
    }
}