package com.zorbeytorunoglu.chatformat.configuration

class ConfigContainer(configResource: Resource) {

    val defaultFormat: String = configResource.getString("chat-format.default")
    val perGroupEnabled: Boolean = configResource.getBoolean("chat-format.per-group.enabled")
    val perGroupFormats: MutableMap<String, String> = mutableMapOf()

    init {
        if (configResource.getConfigurationSection("chat-format.per-group.groups")!=null) {
            for (key in configResource.getConfigurationSection("chat-format.per-group.groups").getKeys(false)) {
                this.perGroupFormats[key] = configResource.getString("chat-format.per-group.groups.$key")
            }
        }
    }

}