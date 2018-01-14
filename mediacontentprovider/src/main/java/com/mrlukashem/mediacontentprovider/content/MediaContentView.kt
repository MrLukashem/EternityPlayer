package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.generic.Buildable
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

data class MediaContentView internal constructor(
        val contentType: ContentType,
        val contentFields: MutableSet<MediaContentField>,
        val contentHandle: Int) {
    constructor(contentType: ContentType, contentFields: MutableSet<MediaContentField>)
    : this(contentType, contentFields, HANDLE_NOT_INITIALIZED)

    fun getFieldValue(fieldName: MediaContentField.FieldName) : String {
        val fieldValue = contentFields.find {
            it.fieldName == fieldName
        }?.fieldValue

        return fieldValue ?: throw NoSuchElementException(
                "The ContentView does not contain ${fieldName.name} field")
    }

    fun isFieldPresent(fieldName: MediaContentField.FieldName) : Boolean {
        return contentFields.find {
            it.fieldName == fieldName
        } != null
    }

    companion object BuilderFactory {
        val HANDLE_NOT_INITIALIZED: Int = -1

        fun build(init: MediaContentViewBuilder.() -> Unit) = MediaContentViewBuilder(init).build()
        fun from(contentBase: MediaContentView) = MediaContentViewBuilder().from(contentBase)
        fun create() = MediaContentViewBuilder()
    }

    class MediaContentViewBuilder() : Buildable<MediaContentView, MediaContentViewBuilder> {
        var contentType: ContentType = ContentType()
        var contentFields: MutableSet<MediaContentField> = HashSet()
        var contentHandle: Int = HANDLE_NOT_INITIALIZED

        constructor(init: MediaContentViewBuilder.() -> Unit): this() {
            init()
        }

        override fun build(): MediaContentView {
            return MediaContentView(contentType, contentFields, contentHandle)
        }

        override fun from(tBase: MediaContentView): MediaContentViewBuilder {
            return MediaContentViewBuilder().apply {
                contentType = tBase.contentType
                contentFields = tBase.contentFields
            }
        }

        override fun reset(): MediaContentViewBuilder {
            contentType = ContentType()
            contentFields.clear()
            contentHandle = HANDLE_NOT_INITIALIZED

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
        fun setFields(newContentFields: MutableSet<MediaContentField>) = apply {
            contentFields = newContentFields
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setField(newContentField: MediaContentField) = apply {
            contentFields.add(newContentField)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setField(fieldName: MediaContentField.FieldName, fieldValue: String) = apply {
            contentFields.add(MediaContentField(fieldName, fieldValue))
        }
    }
}
