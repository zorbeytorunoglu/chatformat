package com.zorbeytorunoglu.chatformat.listeners

import com.zorbeytorunoglu.chatformat.CH
import com.zorbeytorunoglu.chatformat.utils.StringUtils
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class Chat(private val plugin: CH): Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {

        var format: String = plugin.getPapiUtils().getFormat(event.player)

        if (PlaceholderAPI.containsPlaceholders(format)) format = PlaceholderAPI.setPlaceholders(event.player, format)

        format = StringUtils.hex(format)

        format = if (event.player.hasPermission("chatformat.colored")) format
            .replace("%message%", StringUtils.hex(event.message))
        else format.replace("%message%", event.message)

        event.format = format.replace("%", "%%")

    }

}