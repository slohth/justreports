package dev.slohth.justreports.utils.framework.menu

import org.bukkit.inventory.ItemStack
import java.util.LinkedList
import java.util.HashMap

class ShapedMenuPattern {
    private val icons = LinkedList<Char>()
    private val items: MutableMap<Int, ItemStack> = HashMap()
    private val buttons: MutableMap<Int, Button> = HashMap()

    constructor(rows: Array<CharArray>) {
        this.setPattern(rows)
    }

    constructor(rows: Array<String>) {
        this.setPattern(rows)
    }

    constructor(rows: List<String>) {
        this.setPattern(rows)
    }

    operator fun set(c: Char, item: ItemStack) {
        var index = 0
        for (icon in icons) {
            if (icon == c) {
                items[index] = item
            }
            index++
        }
    }

    operator fun set(c: Char, button: Button) {
        var index = 0
        for (icon in icons) {
            if (icon == c) {
                buttons[index] = button
            }
            index++
        }
    }

    fun setPattern(rows: Array<CharArray>) {
        for (row in rows) for (c in row) icons.add(c)
    }

    fun setPattern(rows: Array<String>) {
        for (row in rows) for (c in row.toCharArray()) icons.add(c)
    }

    fun setPattern(rows: List<String>) {
        for (row in rows) for (c in row.toCharArray()) icons.add(c)
    }

    fun clear() {
        icons.clear()
        items.clear()
        buttons.clear()
    }

    fun getItems(): Map<Int, ItemStack> {
        return items
    }

    fun getButtons(): Map<Int, Button> {
        return buttons
    }
}