package dev.slohth.justreports.utils.framework.config

import dev.slohth.justreports.utils.CC

enum class Messages(private var value: String) {

    REPORT_NO_PERMISSION("no perms big man"),
    REPORT_SELF_ERROR("no reporto selfo"),
    REPORT_INVALID_USAGE("fucking get the command right you twit"),
    REPORT_INVALID_REASON("not a valid reason"),
    REPORT_INVALID_PLAYER("no player there pal"),
    REPORT_SUCCESS("good job fucktard");

    fun getValue(): String { return CC.trns(value) }
    fun setValue(str: String) { value = str }

    companion object {
        fun initValues() {
            for (msg: Messages in values()) {
                msg.setValue(Config.MESSAGES.getStringOrDefault(msg.toString().lowercase()
                    .replace("_", "-"), msg.getValue()))
            }
        }
    }

}