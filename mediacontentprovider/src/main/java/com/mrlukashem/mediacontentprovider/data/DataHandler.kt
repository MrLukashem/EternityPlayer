package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.content.IMediaContentView
import com.mrlukashem.mediacontentprovider.types.ContentType

interface DataHandler {
  fun query(queryView: QueryView): List<IMediaContentView>
  fun search(wildCardWorlds: List<String>): List<IMediaContentView>
  fun insert(data: List<IMediaContentView>): ResultType
  fun insert(data: IMediaContentView): ResultType
  fun update(updatedData: List<IMediaContentView>): ResultType
  fun update(updatedData: IMediaContentView): ResultType
  fun delete(data: List<IMediaContentView>): ResultType
  fun delete(data: IMediaContentView): ResultType
  fun delete(contentType: ContentType,
             selectionOptions: List<QueryView.SelectionOption>): ResultType
  fun delete(mainType: ContentType.MainType, subType: ContentType.SubType,
             selectionOptions: List<QueryView.SelectionOption>): ResultType

  enum class ResultType {
    SUCCESS,
    FAILED,
    // TODO: More descriptive errors?
  }
}