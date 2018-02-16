package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentField
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.FieldName

/**
 * Created by MrLukashem on 14.01.2018.
 */
abstract class ContentView(internal val metaData: Map<String, String>) {
    abstract val contentType: ContentType
    abstract val contentFields: MutableSet<ContentField>

    abstract fun containsField(name: FieldName): Boolean
    abstract fun getOrDefault(name: FieldName, defaultValue: String): String
    abstract operator fun get(name: FieldName): String
}