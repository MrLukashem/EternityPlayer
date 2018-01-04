package com.mrlukashem.mediacontentprovider.QueryViewBuilderTests;

import com.mrlukashem.mediacontentprovider.data.QueryView;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by MrLukashem on 04.01.2018.
 */

public class QueryViewBuilderTestsForJava {
    @Test
    void queryViewBuilderCompare() {
        QueryView.QueryViewBuilder builder = new QueryView.QueryViewBuilder();
        QueryView firstView = builder
                .contentType(new ContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK))
                .fieldsProjection(MediaContentField.FieldName.DATA)
                .fieldsProjection(MediaContentField.FieldName.ALBUM)
                .fieldsProjection(MediaContentField.FieldName.TITLE).build();
        QueryView secondView = builder
                .contentType(new ContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK))
                .fieldsProjection(MediaContentField.FieldName.DATA)
                .fieldsProjection(MediaContentField.FieldName.ALBUM)
                .fieldsProjection(MediaContentField.FieldName.TITLE).build();

        Assert.assertTrue(firstView.equals(secondView));
    }
}
