package com.mrlukashem.mediacontentprovider.providers

import com.mrlukashem.mediacontentprovider.content.IMediaContentView
import com.mrlukashem.mediacontentprovider.data.Query
import com.mrlukashem.mediacontentprovider.types.ContentType

interface IMediaContentProvider {
  /*
   *
   */
  fun getContentByKey(key: Int): IMediaContentView
  fun getType(): ContentType
  fun getContent(contentType: ContentType,
                 resultCallback: ((List<IMediaContentView>) -> Unit)? = null, maxCapacity: Int = -1)
  fun getContent(query: Query,
                 resultCallback: ((List<IMediaContentView>) -> Unit)? = null, maxCapacity: Int = -1)
  fun getContentFromParent(contentParent: IMediaContentView,
                           resultCallback: ((List<IMediaContentView>) -> Unit)? = null,
                           maxCapacity: Int = -1)
  fun getContentFromParent(query: Query,
                           resultCallback: ((List<IMediaContentView>) -> Unit)? = null,
                           maxCapacity: Int = -1)
}