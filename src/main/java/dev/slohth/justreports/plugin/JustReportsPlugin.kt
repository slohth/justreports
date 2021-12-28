package dev.slohth.justreports.plugin

import dev.slohth.justreports.JustReports
import org.bukkit.plugin.java.JavaPlugin

class JustReportsPlugin : JavaPlugin() {

    private lateinit var core: JustReports

    override fun onEnable() {
        this.core = JustReports(this)
    }

}