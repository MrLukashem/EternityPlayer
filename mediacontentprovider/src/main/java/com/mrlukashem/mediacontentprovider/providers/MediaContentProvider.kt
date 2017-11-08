package com.mrlukashem.mediacontentprovider.providers

import android.content.Context
import com.mrlukashem.mediacontentprovider.content.IMediaContentView
import com.mrlukashem.mediacontentprovider.data.Query
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaContentProvider(private val appContext: Context) : IMediaContentProvider {
  override fun getContentByKey(key: Int): IMediaContentView {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getType(): ContentType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContent(contentType: ContentType,
                          resultCallback: ((List<IMediaContentView>) -> Unit)?,
                          maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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