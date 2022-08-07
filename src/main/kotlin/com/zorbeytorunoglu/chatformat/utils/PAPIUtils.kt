package com.zorbeytorunoglu.chatformat.utils

import com.zorbeytorunoglu.chatformat.CH
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

class PAPIUtils(private val plugin: CH) {

    private fun getPlayerRank(player: Player): String {

        return PlaceholderAPI.setPlaceholders(player, "%vault_rank%")

    }

    fun getFormat(player: Player): String {

        return if (plugin.getConfigHandler().getGroupFormatEnabled() &&
            plugin.getConfigHandler().getGroupFormats().containsKey(getPlayerRank(player)))
            plugin.getConfigHandler().getGroupFormats()[getPlayerRank(player)]!!
        else plugin.getConfigHandler().getDefaultFormat()

    }

}