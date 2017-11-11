package com.mrlukashem.mediacontentprovider.content

import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

import java.util.HashMap

class MediaContentViewBuilder(private val contentTypeToBuild: ContentType) {
    var initialFields = ArrayList<MediaContentField>()

    constructor(mainType : ContentType.MainType, subType: ContentType.SubType)
            : this(ContentType(mainType, subType)) {}

    fun build(): IMediaContentView {
        return MediaContentView(initialFields, contentTypeToBuild)
    }

    fun addField(field : MediaContentField) : MediaContentViewBuilder {
        initialFields.add(field)
        return this
    }

    fun addField(fieldName: MediaContentField.FieldName, fieldValue: String)
            : MediaContentViewBuilder {
        return addField(MediaContentField(fieldName, fieldValue))
    }
}
