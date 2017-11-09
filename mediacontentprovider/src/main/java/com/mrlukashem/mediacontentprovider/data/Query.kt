package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

class Query(val contentType: ContentType,
            val sortOrder: SortOrder? = SortOrder.ASC,
            val selectionCriteria: List<SelectionCriterion>? = null,
            val fieldsToWithdraw: List<MediaContentField.FieldName>? = null) {

  constructor(contentType: ContentType,
              sortOrder: SortOrder? = SortOrder.ASC,
              selectionCriteria: List<SelectionCriterion>? = null,
              vararg fieldsToWithdraw: MediaContentField.FieldName)
          : this(contentType, sortOrder, selectionCriteria, fieldsToWithdraw.toList()) {}

  enum class SortOrder {
    DESC,
    ASC,
  }

  class SelectionCriterion(val field: MediaContentField, val op: SelectionOperator) {
    enum class SelectionOperator {
      EQUALS,
      EQUALS_GREATER,
      EQUALS_LESS,
      GREATER,
      LESS,
    }
  }
}