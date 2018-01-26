package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.types.ContentType

typealias ContentViews = List<ContentView>
typealias SelectionOptions = List<QueryView.SelectionOption>

interface DataHandler {
    fun query(queryView: QueryView): ContentViews
    fun search(wildCardWorlds: List<String>): ContentViews
    fun insert(data: ContentViews): ResultType
    fun update(updatedData: ContentViews): ResultType
    fun delete(data: ContentViews): ResultType
    fun delete(mainType: ContentType.MainType, subType: ContentType.SubType,
               selectionOptions: SelectionOptions? = null): ResultType

    enum class ResultType {
        SUCCESS,
        FAILED,
    }
}
