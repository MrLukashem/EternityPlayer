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
    public void queryViewBuilderCompare() {
        QueryView firstView = new QueryView.QueryViewBuilder()
                .setContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
                .setFieldProjection(MediaContentField.FieldName.DATA)
                .setFieldProjection(MediaContentField.FieldName.ALBUM)
                .setFieldProjection(MediaContentField.FieldName.TITLE).build();
        QueryView secondView = new QueryView.QueryViewBuilder()
                .setContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
                .setFieldProjection(MediaContentField.FieldName.DATA)
                .setFieldProjection(MediaContentField.FieldName.ALBUM)
                .setFieldProjection(MediaContentField.FieldName.TITLE).build();
        QueryView thirdView = new QueryView.QueryViewBuilder()
                .setContentType(
                        ContentType.MainType.AUDIO, ContentType.SubType.PLAYLIST)
                .setFieldProjection(MediaContentField.FieldName.DATA)
                .setFieldProjection(MediaContentField.FieldName.ALBUM)
                .setFieldProjection(MediaContentField.FieldName.TITLE).build();
        QueryView fourthView = new QueryView.QueryViewBuilder().from(thirdView).build();

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

        QueryView.QueryViewBuilder builder = new QueryView.QueryViewBuilder().setContentType(
                ContentType.MainType.VIDEO, ContentType.SubType.PLAYLIST).reset();
        QueryView emptyViewAfterReset = builder.reset().build();
        QueryView emptyViewByDefault = new QueryView.QueryViewBuilder().build();
        Assert.assertTrue(emptyViewAfterReset.equals(emptyViewByDefault));
        Assert.assertTrue(emptyViewAfterReset.getContentType().equals(
                emptyViewByDefault.getContentType()));

        firstView = new QueryView.QueryViewBuilder()
                .setSelectionOption(
                        new QueryView.SelectionOption(new MediaContentField(
                                MediaContentField.FieldName.ALBUM, "IronMaiden"),
                                QueryView.SelectionOption.SelectionType.EQUALS_GREATER))
                .setSelectionOption(
                        new QueryView.SelectionOption(new MediaContentField(
                                MediaContentField.FieldName.ALBUM, "IronMaiden"),
                                QueryView.SelectionOption.SelectionType.EQUALS_GREATER)).build();

        secondView = new QueryView.QueryViewBuilder().setSelectionOption(
                new QueryView.SelectionOption(new MediaContentField(
                        MediaContentField.FieldName.ALBUM, "IronMaiden"),
                        QueryView.SelectionOption.SelectionType.EQUALS_GREATER)).build();
        Assert.assertTrue(firstView.equals(secondView));
    }
}
