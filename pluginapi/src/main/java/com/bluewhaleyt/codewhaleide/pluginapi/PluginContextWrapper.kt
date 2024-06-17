package com.bluewhaleyt.codewhaleide.pluginapi

import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Resources

open class PluginContextWrapper(
    val context: Context,
    val source: PluginSource
) : ContextWrapper(context) {

    override fun getAssets(): AssetManager? {
        try {
            with(AssetManager::class.java) {
                val assetManager = getConstructor().newInstance()
                val addAssetPath = getMethod("addAssetPath", String::class.java)
                when (source) {
                    is PluginSource.FromAssets -> addAssetPath(assetManager, source.apkFileName)
                    is PluginSource.FromExternalStorage -> addAssetPath(assetManager, source.apkFile.absolutePath)
                }
                return assetManager
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun getResources(): Resources? {
        try {
            @Suppress("DEPRECATION")
            val resources = Resources(assets, context.resources.displayMetrics, context.resources.configuration)
            return resources
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}