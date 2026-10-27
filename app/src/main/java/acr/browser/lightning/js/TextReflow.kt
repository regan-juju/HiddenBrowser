package acr.browser.lightning.js

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * Force the text to reflow.
 */
class TextReflow @Inject constructor(private val assetManager: AssetManager) {

    fun provideJs(): String =
        assetManager.open("js/TextReflow.js").bufferedReader().use { it.readText() }

}