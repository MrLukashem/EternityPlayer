package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.generic.Buildable
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.ContentField

class MediaContentView internal constructor(
        override val contentType: ContentType,
        override val contentFields: MutableSet<ContentField>,
        metaData: Map<String, String>)
    : ContentView(metaData) {
    constructor(contentType: ContentType, contentFields: MutableSet<ContentField>)
          : this(contentType, contentFields, HashMap())

    private fun getFieldValue(fieldName: ContentField.FieldName): String? {
        return contentFields.find {
            it.fieldName == fieldName
        }?.fieldValue
    }

    override fun getOrDefault(name: ContentField.FieldName, defaultValue: String): String {
        return getFieldValue(name) ?: defaultValue
    }

    override operator fun get(name: ContentField.FieldName): String {
        val fieldValue = getFieldValue(name)
        return fieldValue ?: throw NoSuchElementException(
                "The ContentView does not contain ${name.name} field")
    }

    override fun containsField(name: ContentField.FieldName): Boolean {
        return contentFields.find {
            it.fieldName == name
        } != null
    }

    companion object BuilderFactory {
        val HANDLE_NOT_INITIALIZED: Int = -1

        fun build(init: MediaContentViewBuilder.() -> Unit) = MediaContentViewBuilder(init).build()
        fun from(contentBase: ContentView) = MediaContentViewBuilder().from(contentBase)
        fun create() = MediaContentViewBuilder()
    }

    class MediaContentViewBuilder() : Buildable<ContentView, MediaContentViewBuilder> {
        var contentType: ContentType = ContentType()
        var contentFields: MutableSet<ContentField> = HashSet()
        var metaData: MutableMap<String, String> = HashMap()

        constructor(init: MediaContentViewBuilder.() -> Unit): this() {
            init()
        }

        override fun build(): ContentView {
            return MediaContentView(contentType, contentFields, metaData)
        }

        override fun from(tBase: ContentView): MediaContentViewBuilder {
            return MediaContentViewBuilder().apply {
                contentType = tBase.contentType
                contentFields = tBase.contentFields
            }
        }

        override fun reset(): MediaContentViewBuilder {
            contentType = ContentType()
            contentFields.clear()
            metaData.clear()

            return this
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setType(mainType: ContentType.MainType, subType: ContentType.SubType) = apply {
            contentType = ContentType(mainType, subType)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setFields(newContentFields: MutableSet<ContentField>) = apply {
            contentFields = newContentFields
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setField(newContentField: ContentField) = apply {
            contentFields.add(newContentField)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setField(fieldName: ContentField.FieldName, fieldValue: String) = apply {
            contentFields.add(ContentField(fieldName, fieldValue))
        }
    }
}
