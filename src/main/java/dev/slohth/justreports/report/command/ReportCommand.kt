package dev.slohth.justreports.report.command

import dev.slohth.justreports.JustReports
import dev.slohth.justreports.utils.framework.command.Command
import dev.slohth.justreports.utils.framework.command.CommandArgs
import dev.slohth.justreports.utils.framework.command.Completer
import dev.slohth.justreports.utils.framework.command.ICommand
import dev.slohth.justreports.utils.framework.config.Messages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ReportCommand(private val core: JustReports) : ICommand {

    @Command(name = "report", inGameOnly = true)
    fun reportCommand(args: CommandArgs) {
        if (!args.player.hasPermission("reports.submit")) {
            args.player.sendMessage(Messages.REPORT_NO_PERMISSION.getValue()); return
        }
        if (args.length() <= 2) {
            args.player.sendMessage(Messages.REPORT_INVALID_USAGE.getValue()); return
        }

        val target: Player? = Bukkit.getPlayer(args.getArgs(0))

        if (target == null) {
            args.player.sendMessage(Messages.REPORT_INVALID_PLAYER.getValue()); return
        }
        if (target.name == args.player.name) {
            args.player.sendMessage(Messages.REPORT_SELF_ERROR.getValue()); return
        }
        if (!core.getReportManager().isValidReason(args.getArgs(1))) {
            args.player.sendMessage(Messages.REPORT_INVALID_REASON.getValue()); return
        }

        val details: StringBuilder = StringBuilder()
        for (i in 2 until args.length()) details.append(args.getArgs(i)).append(if (i != args.length() - 1) " " else "")

        core.getReportManager().new(target.uniqueId, args.player.uniqueId, args.getArgs(1), details.toString())
        args.player.sendMessage(Messages.REPORT_SUCCESS.getValue())
    }

    @Completer(name = "report")
    fun reportCompleter(args: CommandArgs): List<String>? {
        if (!args.player.hasPermission("reports.submit")) return emptyList()
        return when (args.length()) {
            1 -> null
            2 -> core.getReportManager().getValidReasons()
            else -> emptyList()
        }
    }

}