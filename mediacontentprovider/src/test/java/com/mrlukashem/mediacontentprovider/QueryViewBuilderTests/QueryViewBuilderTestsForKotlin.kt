package com.mrlukashem.mediacontentprovider.QueryViewBuilderTests

import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

import org.junit.Assert
import org.junit.Test

/**
 * Created by MrLukashem on 04.01.2018.
 */
class QueryViewBuilderTestsForKotlin {
    @Test
    fun queryViewBuilderCompare() {
        val firstView = QueryView.build {
            contentType = ContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
            fieldsProjection = listOf(
                    MediaContentField.FieldName.DATA,
                    MediaContentField.FieldName.ALBUM,
                    MediaContentField.FieldName.TITLE)
        }
        val secondView = QueryView.build {
            contentType = ContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
            fieldsProjection = listOf(
                    MediaContentField.FieldName.DATA,
                    MediaContentField.FieldName.ALBUM,
                    MediaContentField.FieldName.TITLE)
        }

        Assert.assertTrue(firstView == secondView)
    }
}