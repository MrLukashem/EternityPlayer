package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.content.MediaContentView
import com.mrlukashem.mediacontentprovider.types.ContentType

interface DataHandler {
  fun query(queryView: QueryView): List<MediaContentView>
  fun search(wildCardWorlds: List<String>): List<MediaContentView>
  fun insert(data: List<MediaContentView>): ResultType
  fun insert(data: MediaContentView): ResultType
  fun update(updatedData: List<MediaContentView>): ResultType
  fun update(updatedData: MediaContentView): ResultType
  fun delete(data: List<MediaContentView>): ResultType
  fun delete(data: MediaContentView): ResultType
  fun delete(contentType: ContentType,
             selectionOptions: List<QueryView.SelectionOption>): ResultType
  fun delete(mainType: ContentType.MainType, subType: ContentType.SubType,
             selectionOptions: List<QueryView.SelectionOption>): ResultType

  enum class ResultType {
    SUCCESS,
    FAILED,
  }
}