package com.zorbeytorunoglu.chatformat.commands

import com.zorbeytorunoglu.chatformat.CH
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CmdChatFormat(private val plugin: CH): CommandExecutor {

    init {
        plugin.getCommand("chatformat").executor = this
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (command.name == "chatformat") {

            if (sender.hasPermission("chatformat.reload"))
            {
                plugin.reloadConfig()
                sender.sendMessage("§e§lCH §7Configurations are reloaded")
                return true
            }

        }

        return false

    }


}