package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

data class MediaContentView internal constructor(
        override val contentFields: List<MediaContentField>, override val contentType: ContentType,
        override val contentHandle: Int)
    : IMediaContentView {

    constructor(contentFields: List<MediaContentField>, contentType: ContentType)
    : this(contentFields, contentType, IMediaContentView.HANDLE_NOT_INITIALIZED)

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
}
