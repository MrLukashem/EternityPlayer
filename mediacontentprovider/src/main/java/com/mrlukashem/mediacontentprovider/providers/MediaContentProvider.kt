package com.mrlukashem.mediacontentprovider.providers

import android.content.Context
import com.mrlukashem.mediacontentprovider.content.ContentView

import com.mrlukashem.mediacontentprovider.data.DataHandler
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.data.SharedPreferencesDataHandler
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaContentProvider() : ContentProvider {
    private var dataHandler: DataHandler? = null

    enum class Mode {
        MEDIA_DB,
        SHARED_PREFERENCES,
    }

    constructor(appContext: Context, mode: Mode = Mode.MEDIA_DB) : this() {
//    dataHandler = when(mode) {
//      Mode.MEDIA_DB -> MediaDatabaseHandler(appContext.contentResolver)
//      Mode.SHARED_PREFERENCES -> SharedPreferencesDataHandler()
//    }
    }

    override fun fetchContent(wildCardWorlds: List<String>,
                              resultCallback: ((List<ContentView>) -> Unit)?,
                              maxCapacity: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchContent(contentType: ContentType,
                              resultCallback: ((List<ContentView>) -> Unit)?,
                              maxCapacity: Int) {
        val content = dataHandler?.query(QueryView(contentType))
        resultCallback?.invoke(content ?: ArrayList())
    }

    override fun fetchContent(queryView: QueryView,
                              resultCallback: ((List<ContentView>) -> Unit)?,
                              maxCapacity: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchContentFromParent(contentParent: ContentView,
                                        resultCallback: ((List<ContentView>) -> Unit)?,
                                        maxCapacity: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchParent(contentChild: ContentView, resultCallback: ResultCallback?, maxCapacity: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}