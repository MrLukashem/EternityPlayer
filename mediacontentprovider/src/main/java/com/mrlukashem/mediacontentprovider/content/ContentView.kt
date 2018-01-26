package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentField
import com.mrlukashem.mediacontentprovider.types.ContentField.*
import com.mrlukashem.mediacontentprovider.types.ContentType

/**
 * Created by MrLukashem on 14.01.2018.
 */
interface ContentView {
  val contentType: ContentType
  val contentFields: MutableSet<ContentField>
  val contentHandle: Int

  fun containsField(name: FieldName): Boolean
  fun getOrDefault(name: FieldName, defaultValue: String): String
  operator fun get(name: FieldName): String
}