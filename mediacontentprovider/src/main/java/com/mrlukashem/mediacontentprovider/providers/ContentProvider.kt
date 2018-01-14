package com.mrlukashem.mediacontentprovider.providers

import com.mrlukashem.mediacontentprovider.content.MediaContentView
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.types.ContentType

typealias ResultCallback = (List<MediaContentView>) -> Unit

interface ContentProvider {
  fun getContentByKey(key: Int): MediaContentView
  fun getType(): ContentType
  fun getContent(contentType: ContentType, resultCallback: ResultCallback? = null,
                 maxCapacity: Int = -1)
  fun getContent(queryView: QueryView, resultCallback: ResultCallback? = null,
                 maxCapacity: Int = -1)
  fun getContent(wildCardWorlds: List<String>, resultCallback: ResultCallback? = null,
                 maxCapacity: Int = -1)
  fun getContentFromParent(contentParent: MediaContentView, resultCallback: ResultCallback? = null,
                           maxCapacity: Int = -1)
  fun getContentFromParent(queryView: QueryView, resultCallback: ResultCallback? = null,
                           maxCapacity: Int = -1)
}