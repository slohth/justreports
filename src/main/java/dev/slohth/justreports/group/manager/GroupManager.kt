package dev.slohth.justreports.group.manager

import dev.slohth.justreports.JustReports
import dev.slohth.justreports.utils.framework.config.Config
import dev.slohth.justreports.group.Group
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class GroupManager(private val core: JustReports) : Listener {

    private val groups: MutableMap<String, Group> = HashMap()

    init { Bukkit.getPluginManager().registerEvents(this, core.getPlugin()) }

    fun registerGroups() {
        groups.clear()
        for (group: String in Config.OPTIONS.getConfig().getConfigurationSection("groups")!!.getKeys(false))
            groups[group] = Group(group)
        registerMembers()
    }

    private fun registerMembers() {
        for (player: Player in Bukkit.getOnlinePlayers())
            for (g: Group in groups.values) if (player.hasPermission(g.getPermissionNode())) g.getMembers().add(player.uniqueId)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        for (g: Group in groups.values)
            if (event.player.hasPermission(g.getPermissionNode())) g.getMembers().add(event.player.uniqueId)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        for (g: Group in groups.values)
            if (g.getMembers().contains(event.player.uniqueId)) g.getMembers().remove(event.player.uniqueId)
    }

    fun getGroup(name: String): Group? {
        for (entry: Map.Entry<String, Group> in groups) if (entry.key.equals(name, ignoreCase = true)) return entry.value
        return null
    }

}