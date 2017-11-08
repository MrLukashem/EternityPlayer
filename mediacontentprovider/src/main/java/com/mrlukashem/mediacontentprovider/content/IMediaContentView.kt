package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

interface IMediaContentView {
  fun getType(): ContentType
  fun getField(fieldName: MediaContentField): String
  fun getKey(): Int
}