package dev.slohth.justreports.report

import dev.slohth.justreports.JustReports
import dev.slohth.justreports.utils.framework.config.Config
import dev.slohth.justreports.group.Group
import dev.slohth.justreports.group.exception.GroupException
import dev.slohth.justreports.report.type.ReportType
import org.bukkit.Material
import java.time.Instant
import java.util.*

class Report(private val core: JustReports, private val reported: UUID, private val reportedBy: UUID, private val reason: String, private val details: String) {

    private val reportedAt: Instant = Instant.now()
    private val type: ReportType = ReportType.of(reason)

    private var requires: Group
    private var assigned: UUID? = null

    init {
        val default: String = Config.OPTIONS.getString("default-report-groups.$type")!!
        requires = core.getGroupManager().getGroup(default) ?: throw GroupException("Default group not found!")
        Material.GRAY_STAINED_GLASS_PANE
    }

    fun getReported(): UUID { return reported }
    fun getReporter(): UUID { return reportedBy }
    fun getReason(): String { return reason }
    fun getDetails(): String { return details }
    fun getTimestamp(): Instant { return reportedAt }
    fun getRequired(): Group { return requires }
    fun getAssigned(): UUID? { return assigned }

    fun setRequired(requires: Group) { this.requires = requires}
    fun setAssigned(assigned: UUID?) { this.assigned = assigned }

}