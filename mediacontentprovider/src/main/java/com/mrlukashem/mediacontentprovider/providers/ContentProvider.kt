package com.mrlukashem.mediacontentprovider.providers

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.types.ContentType

typealias ResultCallback = (List<ContentView>) -> Unit

interface ContentProvider {
  fun getType(): ContentType
  fun fetchContent(contentType: ContentType, resultCallback: ResultCallback? = null,
                   maxCapacity: Int = -1)
  fun fetchContent(queryView: QueryView, resultCallback: ResultCallback? = null,
                   maxCapacity: Int = -1)
  fun fetchContent(wildCardWorlds: List<String>, resultCallback: ResultCallback? = null,
                   maxCapacity: Int = -1)
  fun fetchContentFromParent(contentParent: ContentView, resultCallback: ResultCallback? = null,
                             maxCapacity: Int = -1)
  fun fetchContentFromParent(queryView: QueryView, resultCallback: ResultCallback? = null,
                             maxCapacity: Int = -1)
}