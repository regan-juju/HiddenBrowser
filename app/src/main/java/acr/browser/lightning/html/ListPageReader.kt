package acr.browser.lightning.html

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * The store for the list view HTML.
 */
class ListPageReader @Inject constructor(private val assetManager: AssetManager) {

    fun provideHtml(): String =
        assetManager.open("html/list.html").bufferedReader().use { it.readText() }

}
