package com.zorbeytorunoglu.chatformat

import com.zorbeytorunoglu.kLib.MCPlugin
import com.zorbeytorunoglu.kLib.configuration.Resource
import com.zorbeytorunoglu.kLib.configuration.createYamlResource
import com.zorbeytorunoglu.kLib.extensions.colorHex
import com.zorbeytorunoglu.kLib.extensions.info
import com.zorbeytorunoglu.kLib.extensions.severe
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class CH: MCPlugin() {

    lateinit var config: ConfigContainer

    override fun onEnable() {

        if (!papiExists()) {
            severe("PlaceholderAPI could not be found! Plugin will not work!")
            this.isEnabled = false
            return
        }

        server.pluginManager.registerEvents(ChatListener(this), this)
        getCommand("chatformat")!!.setExecutor(ChatFormatCommand(this))

        reloadConfig()

    }

    override fun reloadConfig() {
        config = ConfigContainer(createYamlResource("config.yml").load())
    }

    private fun getPlayerRank(player: Player): String = PlaceholderAPI.setPlaceholders(player, "%vault_rank%")

    fun getFormat(player: Player): String {

        val rank = getPlayerRank(player)

        info("ranki: $rank")

        info("tüm ranklar:")
        config.perGroupFormats.forEach { key, value ->

            info("key: $key , value: $value")

        }

        return if (config.perGroupEnabled && config.perGroupFormats.containsKey(rank))
            config.perGroupFormats[rank]!!
        else
            config.defaultFormat

    }

    private fun papiExists(): Boolean = server.pluginManager.getPlugin("PlaceholderAPI") != null

}

class ChatListener(private val plugin: CH): Listener {

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {

        var format = plugin.getFormat(event.player)

        if (PlaceholderAPI.containsPlaceholders(format)) format = PlaceholderAPI.setPlaceholders(event.player, format).colorHex

        val hasColoredMessagePermission = event.player.hasPermission("chatformat.colored")

        format = format.replace("%message%",
            if (hasColoredMessagePermission) event.message.colorHex else event.message
        )

        event.format = format.replace("%", "%%")

    }

}

class ConfigContainer(resource: Resource) {

    val defaultFormat: String = resource.getString("chatformat.default") ?: "&7[&r%vault_rankprefix%&7] &f%player_name%&7: &r%message%"
    val perGroupEnabled: Boolean = resource.getBoolean("chatformat.perGroup.enabled")
    val perGroupFormats: MutableMap<String, String> = mutableMapOf()

    init {
        resource.getConfigurationSection("chatformat.perGroup.groups")?.let {
            for (key in it.getKeys(false)) {
                perGroupFormats[key] = resource.getString("chatformat.perGroup.groups.$key") ?: defaultFormat
            }
        }
    }

}

class ChatFormatCommand(private val plugin: CH): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (command.name != "chatformat") return false

        if (!sender.hasPermission("chatformat.reload")) return false

        plugin.reloadConfig()

        sender.sendMessage("§e§lCH §7Configurations are reloaded")

        return true

    }

}