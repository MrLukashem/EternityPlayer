package com.mrlukashem.mediacontentprovider.QueryViewBuilderTests

import com.mrlukashem.mediacontentprovider.data.QueryView
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.ContentType.*
import com.mrlukashem.mediacontentprovider.types.ContentField

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
            fieldsProjection.add(ContentField.FieldName.DATA)
            fieldsProjection.add(ContentField.FieldName.ALBUM)
            fieldsProjection.add(ContentField.FieldName.TITLE)
        }
        var secondView = QueryView.build {
            contentType = TRACK
            fieldsProjection.add(ContentField.FieldName.DATA)
            fieldsProjection.add(ContentField.FieldName.ALBUM)
            fieldsProjection.add(ContentField.FieldName.TITLE)
        }
        val thirdView = QueryView.build {
            contentType = PLAYLIST
            fieldsProjection.add(ContentField.FieldName.DATA)
            fieldsProjection.add(ContentField.FieldName.ALBUM)
            fieldsProjection.add(ContentField.FieldName.TITLE)
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
            selectionOptions.add(QueryView.SelectionOption(ContentField(
                    ContentField.FieldName.ALBUM, "IronMaiden"),
                    QueryView.SelectionOption.SelectionType.EQUALS_GREATER))
            selectionOptions.add(QueryView.SelectionOption(ContentField(
                    ContentField.FieldName.ALBUM, "IronMaiden"),
                    QueryView.SelectionOption.SelectionType.EQUALS_GREATER))
        }

        secondView = QueryView.build {
           selectionOptions.add(QueryView.SelectionOption(ContentField(
                   ContentField.FieldName.ALBUM, "IronMaiden"),
                   QueryView.SelectionOption.SelectionType.EQUALS_GREATER))
        }
        Assert.assertTrue(firstView == secondView)
    }
}