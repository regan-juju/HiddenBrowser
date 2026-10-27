package acr.browser.lightning.js

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * Invert the color of the page.
 */
class InvertPage @Inject constructor(private val assetManager: AssetManager) {

    fun provideJs(): String =
        assetManager.open("js/InvertPage.js").bufferedReader().use { it.readText() }

}