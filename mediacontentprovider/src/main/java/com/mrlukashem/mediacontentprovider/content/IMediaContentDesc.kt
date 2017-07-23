package com.mrlukashem.mediacontentprovider.content

interface IMediaContentDesc {
  fun getType(): MediaContentDesc.Type
  fun getField(fieldName: MediaContentDesc.Field): String
  fun getKey(): Int
}