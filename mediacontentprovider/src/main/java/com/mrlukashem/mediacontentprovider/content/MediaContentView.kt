package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.generic.Builder
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

data class MediaContentView internal constructor(
        override val contentType: ContentType,
        override var contentFields: MutableSet<MediaContentField>,
        override val contentHandle: Int)
    : IMediaContentView {
    constructor(contentType: ContentType, contentFields: MutableSet<MediaContentField>)
    : this(contentType, contentFields, IMediaContentView.HANDLE_NOT_INITIALIZED)

    override fun getFieldValue(fieldName: MediaContentField.FieldName) : String {
        val fieldValue = contentFields.find {
            it.fieldName == fieldName
        }?.fieldValue

        return fieldValue ?: ""
    }

    override fun isFieldPresent(fieldName: MediaContentField.FieldName) : Boolean {
        return contentFields.find {
            it.fieldName == fieldName
        } != null
    }

    companion object ContentBuilder {
        fun build(init: MediaContentViewBuilder.() -> Unit) = MediaContentViewBuilder(init).build()
        fun from(contentBase: IMediaContentView) = MediaContentViewBuilder().from(contentBase)
        fun create() = MediaContentViewBuilder()
    }

    class MediaContentViewBuilder() : Builder<IMediaContentView> {
        var contentType: ContentType = ContentType()
        var contentFields: MutableSet<MediaContentField> = HashSet()
        var contentHandle: Int = IMediaContentView.HANDLE_NOT_INITIALIZED

        constructor(init: MediaContentViewBuilder.() -> Unit): this() {
            init()
        }

        override fun build(): IMediaContentView {
            return MediaContentView(contentType, contentFields, contentHandle)
        }

        fun from(tBase: IMediaContentView): MediaContentViewBuilder {
            return MediaContentViewBuilder().apply {
                contentType = tBase.contentType
                contentFields = tBase.contentFields
            }
        }

        fun reset(): MediaContentViewBuilder {
            contentType = ContentType()
            contentFields.clear()
            contentHandle = IMediaContentView.HANDLE_NOT_INITIALIZED

            return this
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun contentType(mainType: ContentType.MainType, subType: ContentType.SubType) = apply {
            contentType = ContentType(mainType, subType)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun contentType(newContentType: ContentType) = apply {
            contentType = newContentType
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun contentFields(newContentFields: MutableSet<MediaContentField>) = apply {
            contentFields = newContentFields
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun contentField(newContentField: MediaContentField) = apply {
            contentFields.add(newContentField)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun contentField(fieldName: MediaContentField.FieldName, fieldValue: String) = apply {
            contentFields.add(MediaContentField(fieldName, fieldValue))
        }
    }
}
