package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.types.MediaContentDescField
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType

class Query(val contentType: MediaContentDescType,
            val sortOrder: String,
            vararg fieldsToWithdraw: MediaContentDescField) {
  val fields: List<MediaContentDescField> = fieldsToWithdraw.toList()
}