package dev.slohth.justreports.utils.framework.config

import dev.slohth.justreports.plugin.JustReportsPlugin
import dev.slohth.justreports.utils.CC
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

enum class Config(private val identifier: String) {

    OPTIONS("config"), MESSAGES("messages");

    private val file: File
    private val config: YamlConfiguration

    init {
        val core: JavaPlugin = JavaPlugin.getPlugin(JustReportsPlugin::class.java)
        this.file = File(core.dataFolder, "$identifier.yml")
        core.saveResource("$identifier.yml", false)
        this.config = YamlConfiguration.loadConfiguration(file)
    }

    fun getString(path: String): String? {
        return if (config.contains(path)) CC.trns(config.getString(path)!!) else null
    }

    fun getStringOrDefault(path: String, def: String): String {
        return getString(path) ?: def
    }

    fun getInteger(path: String): Int {
        return if (config.contains(path)) config.getInt(path) else 0
    }

    fun getBoolean(path: String): Boolean {
        return config.contains(path) && config.getBoolean(path)
    }

    fun getDouble(path: String): Double {
        return if (config.contains(path)) config.getDouble(path) else 0.0
    }

    fun getStringList(path: String): List<String> {
        return if (config.contains(path)) CC.trns(config.getStringList(path)) else ArrayList()
    }

    fun getConfig(): YamlConfiguration { return config }

    fun reloadConfig() {
        try { config.load(file) }
        catch (ignored: IOException) {}
        catch (ignored: InvalidConfigurationException) {}
    }

    fun saveConfig() {
        try { config.save(file) } catch (ignored: IOException) {}
    }

    companion object {
        fun saveAll() { for (c in values()) c.saveConfig() }
        fun reloadAll() { for (c in values()) c.reloadConfig() }
    }
}