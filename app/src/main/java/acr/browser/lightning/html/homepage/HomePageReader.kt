package acr.browser.lightning.html.homepage

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * The store for the homepage HTML.
 */
class HomePageReader @Inject constructor(private val assetManager: AssetManager) {

    fun provideHtml(): String =
        assetManager.open("html/homepage.html").bufferedReader().use { it.readText() }

}