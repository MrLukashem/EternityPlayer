package com.mrlukashem.mediacontentprovider.providers

import android.content.Context

import com.mrlukashem.mediacontentprovider.content.MediaContentView
import com.mrlukashem.mediacontentprovider.data.DataHandler
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaContentProvider() : ContentProvider {
  private var dataHandler: DataHandler? = null

  enum class Mode {
    MEDIA_DB,
    SHARED_PREFERENCES,
  }

  constructor(appContext: Context?, mode: Mode = Mode.MEDIA_DB) : this() {
    dataHandler = when(mode) {
      Mode.MEDIA_DB -> MediaDatabaseHandler(appContext?.contentResolver)
      Mode.SHARED_PREFERENCES -> MediaDatabaseHandler()
    }
  }

  override fun getContentByKey(key: Int): MediaContentView {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getType(): ContentType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContent(wildCardWorlds: List<String>,
                          resultCallback: ((List<MediaContentView>) -> Unit)?,
                          maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContent(contentType: ContentType,
                          resultCallback: ((List<MediaContentView>) -> Unit)?,
                          maxCapacity: Int) {
    val content = dataHandler?.query(QueryView(contentType))
    resultCallback?.invoke(content ?: ArrayList())
  }

  override fun getContent(queryView: QueryView,
                          resultCallback: ((List<MediaContentView>) -> Unit)?,
                          maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentFromParent(contentParent: MediaContentView,
                                    resultCallback: ((List<MediaContentView>) -> Unit)?,
                                    maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentFromParent(queryView: QueryView,
                                    resultCallback: ((List<MediaContentView>) -> Unit)?,
                                    maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}