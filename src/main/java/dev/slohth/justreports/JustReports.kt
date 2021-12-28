package dev.slohth.justreports

import dev.slohth.justreports.group.manager.GroupManager
import dev.slohth.justreports.plugin.JustReportsPlugin
import dev.slohth.justreports.report.command.ReportCommand
import dev.slohth.justreports.report.manager.ReportManager
import dev.slohth.justreports.report.type.ReportType
import dev.slohth.justreports.utils.framework.command.Framework
import dev.slohth.justreports.utils.framework.command.ICommand
import dev.slohth.justreports.utils.framework.config.Messages
import org.bukkit.plugin.java.JavaPlugin

class JustReports (private val plugin: JustReportsPlugin) {

    private val framework: Framework = Framework(plugin)

    private val groupManager: GroupManager = GroupManager(this)
    private val reportManager: ReportManager = ReportManager(this)

    init {
        Messages.initValues()
        ReportType.initValues()
        groupManager.registerGroups()

        for (cmd: ICommand in listOf<ICommand>(ReportCommand(this), TestCommand(this)))
            framework.registerCommands(cmd)
    }

    fun getPlugin(): JavaPlugin { return plugin }
    fun getGroupManager(): GroupManager { return groupManager }
    fun getReportManager(): ReportManager { return reportManager }

}