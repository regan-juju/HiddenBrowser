package acr.browser.lightning.js

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * Reads the theme color from the DOM.
 */
class ThemeColor @Inject constructor(private val assetManager: AssetManager) {

    fun provideJs(): String =
        assetManager.open("js/ThemeColor.js").bufferedReader().use { it.readText() }

}