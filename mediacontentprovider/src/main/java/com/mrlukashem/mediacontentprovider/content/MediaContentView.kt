package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

class MediaContentView internal constructor(override val contentFields: List<MediaContentField>,
                                            override val contentType: ContentType)
    : IMediaContentView {
    override var contentKey: Int = 0
    private set

    private fun initializeKey(initialFields: List<MediaContentField>) {
        val baseString = StringBuilder(contentType.toString())
        initialFields.forEach {
            baseString.append(it.fieldValue)
        }

        contentKey = baseString.toString().hashCode()
    }

    init {
        initializeKey(contentFields)
    }

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
