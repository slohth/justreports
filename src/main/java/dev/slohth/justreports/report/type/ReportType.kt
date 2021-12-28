package dev.slohth.justreports.report.type

import dev.slohth.justreports.utils.framework.config.Config

enum class ReportType(private val reasons: MutableList<String>) {
    CHAT(ArrayList()), GAMEPLAY(ArrayList());

    companion object {
        fun initValues() {
            for (str: String in Config.OPTIONS.getConfig().getConfigurationSection("reasons")!!.getKeys(false)) {
                valueOf(Config.OPTIONS.getString("reasons.$str")!!).reasons.add(str)
            }
        }
        fun of(reason: String): ReportType {
            for (r: String in GAMEPLAY.reasons) if (r.equals(reason, ignoreCase = true)) return GAMEPLAY
            return CHAT
        }
    }
}