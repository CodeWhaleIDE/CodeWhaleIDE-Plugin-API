package com.bluewhaleyt.codewhaleide.pluginapi

import java.io.File

sealed interface PluginSource {

    data class FromAssets(
        val apkFileName: String
    ) : PluginSource

    data class FromExternalStorage(
        val apkFile: File
    ) : PluginSource

}