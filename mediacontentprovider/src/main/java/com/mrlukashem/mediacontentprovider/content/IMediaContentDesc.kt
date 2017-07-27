package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.MediaContentDescField
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType

interface IMediaContentDesc {
  fun getType(): MediaContentDescType
  fun getField(fieldName: MediaContentDescField): String
  fun getKey(): Int
}