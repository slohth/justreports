package dev.slohth.justreports.report.manager

import com.google.gson.Gson
import dev.slohth.justreports.JustReports
import dev.slohth.justreports.report.Report
import dev.slohth.justreports.utils.framework.config.Config
import dev.slohth.justreports.utils.framework.menu.Menu
import dev.slohth.justreports.utils.framework.menu.ShapedMenuPattern
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class ReportManager(private val core: JustReports) {

    private val reports: MutableList<Report> = LinkedList()
    private val validReasons: MutableList<String> = ArrayList()

    private val date: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.UK).withZone(ZoneId.systemDefault())
    private val time: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss").withLocale(Locale.UK).withZone(ZoneId.systemDefault());

    init { registerReasons() }

    fun new(reported: UUID, reportedBy: UUID, reason: String, details: String) {
        val report: Report = Report(core, reported, reportedBy, reason, details)
        reports.add(report)

//        Bukkit.getScheduler().runTaskLater(core.getPlugin(), Runnable {
//            for (player: Player in Bukkit.getOnlinePlayers()) {
//                player.sendMessage(" ")
//                player.sendMessage("Reported: " + Bukkit.getOfflinePlayer(report.getReported()).name)
//                player.sendMessage("Reported By: " + Bukkit.getOfflinePlayer(report.getReporter()).name)
//                player.sendMessage("Reported At: " + date.format(report.getTimestamp()) + " @ " + time.format(report.getTimestamp()))
//                player.sendMessage("Reason: " + report.getReason())
//                player.sendMessage("Details: " + report.getDetails())
//                player.sendMessage("Required Group: " + report.getRequired().getDisplay())
//                player.sendMessage("Assigned To: " + if (report.getAssigned() == null) "N/A" else Bukkit.getOfflinePlayer(report.getAssigned()!!).name)
//                player.sendMessage(" ")
//            }
//        }, 1)
    }

    fun openReportMenu() {
        val menu: Menu = object: Menu(core.getPlugin(), "Report Queue", 6) {}
        val pattern: ShapedMenuPattern = ShapedMenuPattern(listOf(
            "",
            "",
            "",
            "",
            "",
            ""
        ))
    }

    fun saveReports() {
        Gson().toJson(reports[0], Report::class.java)
    }

    fun registerReasons() {
        validReasons.clear()
        validReasons.addAll(Config.OPTIONS.getConfig().getConfigurationSection("reasons")!!.getKeys(false))
    }

    fun isValidReason(reason: String): Boolean {
        for (r: String in validReasons) if (reason.equals(r, ignoreCase = true)) return true
        return false
    }

    fun getValidReasons(): List<String> { return validReasons }

}