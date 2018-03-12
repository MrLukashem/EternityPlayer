package com.mrlukashem.mediacontentprovider.QueryViewBuilderTests

import com.mrlukashem.mediacontentprovider.data.*
import com.mrlukashem.mediacontentprovider.data.ALBUM
import com.mrlukashem.mediacontentprovider.types.ContentType.*
import com.mrlukashem.mediacontentprovider.types.ContentType.TRACK
import com.mrlukashem.mediacontentprovider.types.FieldName

import org.junit.Assert
import org.junit.Test

/**
 * Created by MrLukashem on 04.01.2018.
 */
class QueryViewBuilderTestsForKotlin {
    @Test
    fun queryViewBuilderCompare() {
        var firstView = QueryView.build {
            contentType = TRACK
            fieldsProjection.add(FieldName.DATA)
            fieldsProjection.add(FieldName.ALBUM)
            fieldsProjection.add(FieldName.TITLE)
        }
        var secondView = QueryView.build {
            contentType = TRACK
            fieldsProjection.add(FieldName.DATA)
            fieldsProjection.add(FieldName.ALBUM)
            fieldsProjection.add(FieldName.TITLE)
        }
        val thirdView = QueryView.build {
            contentType = PLAYLIST
            fieldsProjection.add(FieldName.DATA)
            fieldsProjection.add(FieldName.ALBUM)
            fieldsProjection.add(FieldName.TITLE)
        }
        val fourthView = QueryView.from(thirdView).build()

        Assert.assertTrue(firstView == secondView)
        Assert.assertTrue(firstView != thirdView)
        Assert.assertTrue(secondView != thirdView)
        Assert.assertTrue(firstView.contentType == secondView.contentType)
        Assert.assertTrue(firstView.fieldsProjection == secondView.fieldsProjection)
        Assert.assertTrue(firstView.fieldsProjection == thirdView.fieldsProjection)
        Assert.assertTrue(secondView.fieldsProjection == thirdView.fieldsProjection)
        Assert.assertTrue(thirdView == fourthView)
        Assert.assertFalse(thirdView == firstView)
        Assert.assertFalse(thirdView == secondView)

        val builder = QueryView.QueryViewBuilder().setType(PLAYLIST).reset()
        val emptyViewAfterReset = builder.reset().build()
        val emptyViewByDefault = QueryView.build {}
        Assert.assertTrue(emptyViewAfterReset == emptyViewByDefault)
        Assert.assertTrue(emptyViewAfterReset.contentType == emptyViewByDefault.contentType)

        firstView = QueryView.build {
            rawSelectionOptions.add(ALBUM equals "IronMaiden")
        }

        secondView = QueryView.build {
            rawSelectionOptions.add(ALBUM equals "IronMaiden")
        }
        Assert.assertTrue(firstView == secondView)
    }
}