package acr.browser.lightning.html.bookmark

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * The store for the bookmarks HTML.
 */
class BookmarkPageReader @Inject constructor(private val assetManager: AssetManager) {

    fun provideHtml(): String =
        assetManager.open("html/bookmarks.html").bufferedReader().use { it.readText() }

}