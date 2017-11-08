package com.mrlukashem.mediacontentprovider.providers

import android.content.Context

import com.mrlukashem.mediacontentprovider.content.IMediaContentView
import com.mrlukashem.mediacontentprovider.data.DataHandler
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler
import com.mrlukashem.mediacontentprovider.data.Query
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaContentProvider() : IMediaContentProvider {
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

  override fun getContentByKey(key: Int): IMediaContentView {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getType(): ContentType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContent(contentType: ContentType,
                          resultCallback: ((List<IMediaContentView>) -> Unit)?,
                          maxCapacity: Int) {
    val content = dataHandler?.query(Query(contentType, null, null, null))
    resultCallback?.invoke(content ?: ArrayList())
  }

  override fun getContent(query: Query,
                          resultCallback: ((List<IMediaContentView>) -> Unit)?,
                          maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentFromParent(contentParent: IMediaContentView,
                                    resultCallback: ((List<IMediaContentView>) -> Unit)?,
                                    maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentFromParent(query: Query,
                                    resultCallback: ((List<IMediaContentView>) -> Unit)?,
                                    maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}