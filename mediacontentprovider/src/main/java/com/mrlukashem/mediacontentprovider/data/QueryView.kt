package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

class QueryView(val contentType: ContentType,
                val sortOption: SortOption? = null,
                val selectionOptions: List<SelectionOption>? = null,
                val fieldsProjection: List<MediaContentField.FieldName>? = null) {

  constructor(contentType: ContentType,
              sortOption: SortOption? = null,
              selectionOptions: List<SelectionOption>? = null,
              vararg fieldsProjection: MediaContentField.FieldName)
          : this(contentType, sortOption, selectionOptions, fieldsProjection.toList())

  constructor(mainType: ContentType.MainType,
              subType: ContentType.SubType,
              sortOption: SortOption? = null,
              selectionOptions: List<SelectionOption>? = null,
              vararg fieldsProjection: MediaContentField.FieldName)
          : this(ContentType(mainType, subType), sortOption, selectionOptions)

  class SortOption(val field: MediaContentField.FieldName, val sortType: SortType) {
    enum class SortType {
      DESC,
      ASC,
    }
  }

  class SelectionOption(val field: MediaContentField, val type: SelectionType) {
    enum class SelectionType {
      EQUALS,
      EQUALS_GREATER,
      EQUALS_LESS,
      GREATER,
      LESS,
    }
  }
}