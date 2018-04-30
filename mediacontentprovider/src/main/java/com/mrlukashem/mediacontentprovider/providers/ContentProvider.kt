package com.mrlukashem.mediacontentprovider.providers

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.types.ContentType

typealias ResultCallback = (List<ContentView>) -> Unit
typealias ContentViews = List<ContentView>

interface ContentProvider {
    fun fetchContent(contentType: ContentType, resultCallback: ResultCallback,
                     maxCapacity: Int = -1)
    fun fetchContent(queryView: QueryView, maxCapacity: Int = -1): ContentViews
    fun fetchContent(wildCardWorlds: List<String>, resultCallback: ResultCallback? = null,
                     maxCapacity: Int = -1)
    fun fetchChildren(contentParent: ContentView, resultCallback: ResultCallback)
    fun fetchParent(contentChild: ContentView, resultCallback: ResultCallback)
}