package com.mrlukashem.mediacontentprovider.QueryViewBuilderTests;

import com.mrlukashem.mediacontentprovider.data.QueryView;
import com.mrlukashem.mediacontentprovider.data.QueryView.SelectionOption.*;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.ContentField;
import com.mrlukashem.mediacontentprovider.types.FieldName;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by MrLukashem on 04.01.2018.
 */

public class QueryViewBuilderTestsForJava {
    @Test
    public void queryViewBuilderCompare() {
        QueryView firstView = QueryView.QueryBuilder.create()
                .setType(ContentType.TRACK)
                .setFieldProjection(FieldName.DATA)
                .setFieldProjection(FieldName.ALBUM)
                .setFieldProjection(FieldName.TITLE).build();
        QueryView secondView = QueryView.QueryBuilder.create()
                .setType(ContentType.TRACK)
                .setFieldProjection(FieldName.DATA)
                .setFieldProjection(FieldName.ALBUM)
                .setFieldProjection(FieldName.TITLE).build();
        QueryView thirdView = QueryView.QueryBuilder.create()
                .setType(ContentType.PLAYLIST)
                .setFieldProjection(FieldName.DATA)
                .setFieldProjection(FieldName.ALBUM)
                .setFieldProjection(FieldName.TITLE).build();
        QueryView fourthView = QueryView.QueryBuilder.create().from(thirdView).build();

        Assert.assertTrue(firstView.equals(secondView));
        Assert.assertTrue(!firstView.equals(thirdView));
        Assert.assertTrue(!secondView.equals(thirdView));
        Assert.assertTrue(firstView.getContentType().equals(secondView.getContentType()));
        Assert.assertTrue(firstView.getFieldsProjection().equals(secondView.getFieldsProjection()));
        Assert.assertTrue(firstView.getFieldsProjection().equals(thirdView.getFieldsProjection()));
        Assert.assertTrue(secondView.getFieldsProjection().equals(thirdView.getFieldsProjection()));
        Assert.assertTrue(thirdView.equals(fourthView));
        Assert.assertFalse(thirdView.equals(firstView));
        Assert.assertFalse(thirdView.equals(secondView));

        QueryView.QueryViewBuilder builder = QueryView.QueryBuilder.create().setType(
                ContentType.PLAYLIST).reset();
        QueryView emptyViewAfterReset = builder.reset().build();
        QueryView emptyViewByDefault = QueryView.QueryBuilder.create().build();
        Assert.assertTrue(emptyViewAfterReset.equals(emptyViewByDefault));
        Assert.assertTrue(emptyViewAfterReset.getContentType().equals(
                emptyViewByDefault.getContentType()));

        firstView = QueryView.QueryBuilder.create()
                .setSelectionOption(
                        new QueryView.SelectionOption(new ContentField(
                                FieldName.ALBUM, "IronMaiden"), SelectionType.EG))
                .setSelectionOption(
                        new QueryView.SelectionOption(new ContentField(
                                FieldName.ALBUM, "IronMaiden"),
                                SelectionType.EG)).build();

        secondView = QueryView.QueryBuilder.create().setSelectionOption(
                new QueryView.SelectionOption(new ContentField(
                        FieldName.ALBUM, "IronMaiden"), SelectionType.EG)).build();
        Assert.assertTrue(firstView.equals(secondView));
    }
}
