package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField
import com.mrlukashem.mediacontentprovider.types.SelectionCriterion
import com.mrlukashem.mediacontentprovider.types.SortOrder

class Query(val contentType: ContentType,
            val sortOrder: SortOrder? = null,
            val selectionCriteria: List<SelectionCriterion>? = null,
            val fieldsToWithdraw: List<MediaContentField>? = null) {

  constructor(contentType: ContentType,
              sortOrder: SortOrder,
              selectionCriteria: List<SelectionCriterion>,
              vararg fieldsToWithdraw: MediaContentField)
          : this(contentType, sortOrder, selectionCriteria, fieldsToWithdraw.toList()) {}
}