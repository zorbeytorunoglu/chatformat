package com.zorbeytorunoglu.chatformat.configuration

class ConfigHandler(private val configContainer: ConfigContainer) {

    fun getDefaultFormat(): String {
        return configContainer.defaultFormat
    }

    fun getGroupFormatEnabled(): Boolean {
        return configContainer.perGroupEnabled
    }

    fun getGroupFormats(): MutableMap<String, String> {
        return configContainer.perGroupFormats
    }

}