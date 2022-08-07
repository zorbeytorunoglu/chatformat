package com.zorbeytorunoglu.chatformat

import com.zorbeytorunoglu.chatformat.configuration.ConfigContainer
import com.zorbeytorunoglu.chatformat.configuration.ConfigHandler
import com.zorbeytorunoglu.chatformat.configuration.Resource
import com.zorbeytorunoglu.chatformat.listeners.Chat
import com.zorbeytorunoglu.chatformat.utils.PAPIUtils
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level
import kotlin.math.log

class CH: JavaPlugin() {

    private lateinit var configResource: Resource
    private lateinit var configHandler: ConfigHandler
    private lateinit var papiUtils: PAPIUtils

    override fun onEnable() {

        configResource = Resource(this, "config.yml")
        configResource.load()
        configHandler = ConfigHandler(ConfigContainer(configResource))

        if (!papiExists()) {
            logger.log(Level.SEVERE, "[ChatFormat] Chat format can not work without PlaceholderAPI plugin." +
                    " Please download the plugin and restart the server.")
            this.isEnabled = false
        }

        papiUtils = PAPIUtils(this)

        Chat(this)

    }

    fun getConfigHandler(): ConfigHandler {
        return configHandler
    }

    fun getPapiUtils(): PAPIUtils {
        return papiUtils
    }

    private fun papiExists(): Boolean {

        return Bukkit.getServer().pluginManager.getPlugin("PlaceholderAPI")!=null

    }

}