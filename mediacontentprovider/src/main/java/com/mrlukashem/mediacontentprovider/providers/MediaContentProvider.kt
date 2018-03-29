package com.mrlukashem.mediacontentprovider.providers

import android.content.Context

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.data.DataHandler
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.data.SharedPreferencesDataHandler
import com.mrlukashem.mediacontentprovider.multithreading.Dispatcher
import com.mrlukashem.mediacontentprovider.multithreading.TasksDispatcher
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaContentProvider(appContext: Context, mode: Mode = Mode.MEDIA_DB) : ContentProvider {
    private var dataHandler: DataHandler
    private val dispatcher: Dispatcher<ContentViews> = TasksDispatcher()

    enum class Mode {
        MEDIA_DB,
        SHARED_PREFERENCES,
    }

    init {
        dataHandler = when(mode) {
            Mode.MEDIA_DB -> MediaDatabaseHandler(appContext.contentResolver)
            Mode.SHARED_PREFERENCES -> SharedPreferencesDataHandler()
        }
        dispatcher.begin()
    }

    override fun fetchContent(wildCardWorlds: List<String>,
                              resultCallback: ((List<ContentView>) -> Unit)?,
                              maxCapacity: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchContent(contentType: ContentType,
                              resultCallback: ((List<ContentView>) -> Unit)?,
                              maxCapacity: Int) {
        if (resultCallback != null) {
            dispatcher.dispatch({ queryInternal(contentType) }, resultCallback)
        } else {
            queryInternal(contentType)
        }
    }

    private fun queryInternal(contentType: ContentType) = synchronized(dataHandler) {
        dataHandler.query(QueryView(contentType))
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