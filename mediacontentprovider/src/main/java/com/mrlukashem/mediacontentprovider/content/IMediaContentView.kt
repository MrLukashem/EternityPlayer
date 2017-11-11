package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

interface IMediaContentView {
  val contentKey: Int
  val contentType: ContentType
  val contentFields: List<MediaContentField>
  fun getFieldValue(fieldName: MediaContentField.FieldName): String
  fun isFieldPresent(fieldName: MediaContentField.FieldName) : Boolean
}