package dev.slohth.justreports

import dev.slohth.justreports.utils.CC
import dev.slohth.justreports.utils.ItemStackSerializer
import dev.slohth.justreports.utils.TextComponentBuilder
import dev.slohth.justreports.utils.framework.command.Command
import dev.slohth.justreports.utils.framework.command.CommandArgs
import dev.slohth.justreports.utils.framework.command.ICommand
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.chat.ComponentSerializer
import net.md_5.bungee.chat.TextComponentSerializer
import net.minecraft.network.chat.IChatBaseComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TestCommand(private val core: JustReports): ICommand {

    @Command(name = "test", inGameOnly = true)
    fun testCommand(args: CommandArgs) {
        val player: Player = args.sender as Player
        if (args.length() >= 1) {
            val input: StringBuilder = StringBuilder();
            for (i in 0 until args.length()) {
                input.append(args.getArgs(i)).append(if (i != (args.length() - 1)) " " else "")
            }
            player.sendMessage(input.toString())
            player.inventory.addItem(ItemStackSerializer.deserialize(input.toString()))
        } else {
            val serialized: String = ItemStackSerializer.serialize(args.player.inventory.itemInMainHand)

            val text: TextComponent = TextComponent("Click to copy serialization!")
            text.clickEvent = ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                ComponentSerializer.toString(TextComponent(serialized)))
            player.spigot().sendMessage(text)

            player.sendMessage(serialized) // does not send
            player.sendMessage("1") // sends

            player.sendRawMessage(serialized) // does not send
            player.sendMessage("2") // sends

            player.spigot().sendMessage(TextComponent(serialized)) // sends
            player.spigot().sendMessage(TextComponent("3")) // sends

            Bukkit.getLogger().info(serialized) // sends
            Bukkit.getLogger().info("4") // sends
        }
    }

}