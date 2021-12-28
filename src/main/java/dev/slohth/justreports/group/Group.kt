package dev.slohth.justreports.group

import dev.slohth.justreports.utils.framework.config.Config
import java.util.*
import kotlin.collections.HashSet

class Group(private val name: String) {

    private val members: MutableSet<UUID> = HashSet()
    private val display: String = Config.OPTIONS.getStringOrDefault("groups.$name", "")

    fun getName(): String { return name }
    fun getMembers(): MutableSet<UUID> { return members }
    fun getDisplay(): String { return display }
    fun getPermissionNode(): String { return "reports.${name.lowercase()}" }

}