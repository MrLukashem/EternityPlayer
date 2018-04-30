package com.mrlukashem.mediacontentprovider.providers

import android.content.Context

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.data.ContentViews
import com.mrlukashem.mediacontentprovider.data.DataHandler
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.data.SharedPreferencesDataHandler
import com.mrlukashem.mediacontentprovider.multithreading.*
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaContentProvider(appContext: Context, mode: Mode = Mode.MEDIA_DB) : ContentProvider {
    private var dataHandler: DataHandler
    private val asyncDispatcher: Dispatcher<ContentViews> = TasksDispatcher()
    private val localDispatcher: Dispatcher<ContentViews> = MainThreadDispatcher()

    private fun dispatch(task: Task<ContentViews>,
                         resultCallback: Callback<ContentViews>? = null): ContentViews {
        var result: ContentViews = emptyList()
        if (resultCallback != null) {
            asyncDispatcher.dispatch(task, resultCallback)
        } else {
            localDispatcher.dispatch(task) { result = it }
        }

        return result
    }

    enum class Mode {
        MEDIA_DB,
        SHARED_PREFERENCES,
    }

    init {
        dataHandler = when(mode) {
            Mode.MEDIA_DB -> MediaDatabaseHandler(appContext.contentResolver)
            Mode.SHARED_PREFERENCES -> SharedPreferencesDataHandler()
        }

        asyncDispatcher.begin()
        localDispatcher.begin()
    }

    override fun fetchContent(wildCardWorlds: List<String>,
                              resultCallback: ((List<ContentView>) -> Unit)?,
                              maxCapacity: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.\
    }

    override fun fetchContent(contentType: ContentType,
                              resultCallback: ((List<ContentView>) -> Unit),
                              maxCapacity: Int) {
        dispatch({ dataHandler.query(QueryView(contentType)) }, resultCallback)
    }

    override fun fetchContent(queryView: QueryView, maxCapacity: Int): ContentViews {
        return dispatch({ dataHandler.query(queryView) })
    }

    private fun queryInternal(queryView: QueryView) = synchronized(dataHandler) {
        dataHandler.query(queryView)
    }

    override fun fetchChildren(contentParent: ContentView, resultCallback: ResultCallback) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchParent(contentChild: ContentView, resultCallback: ResultCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}