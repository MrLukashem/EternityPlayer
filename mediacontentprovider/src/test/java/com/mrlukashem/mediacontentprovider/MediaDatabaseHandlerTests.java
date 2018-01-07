package com.mrlukashem.mediacontentprovider;

import android.test.mock.MockContentResolver;

import com.mrlukashem.mediacontentprovider.content.IMediaContentView;
import com.mrlukashem.mediacontentprovider.data.DataHandler.*;
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler;
import com.mrlukashem.mediacontentprovider.data.QueryView;
import com.mrlukashem.mediacontentprovider.mocks.ContentProviderDataInfo;
import com.mrlukashem.mediacontentprovider.mocks.CustomMockContentProvider;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class MediaDatabaseHandlerTests {
    final private ContentProviderDataInfo providerInfo = new CustomMockContentProvider();
    private MediaDatabaseHandler handler = null;

    public MediaDatabaseHandlerTests() {
        MockContentResolver resolver = new MockContentResolver();
        resolver.addProvider("MockProvider", providerInfo.getProvider());
        handler = new MediaDatabaseHandler(resolver);
    }

    @Test
    public void testQuery() {
        providerInfo.reset();
        QueryView queryView = new QueryView(
                ContentType.MainType.AUDIO,
                ContentType.SubType.TRACK,
                MediaContentField.FieldName.TITLE, MediaContentField.FieldName.ALBUM);
        List<IMediaContentView> tracks = handler.query(queryView);
        Assert.assertTrue(tracks.size() == providerInfo.getTotalTracks());
    }

    @Test
    public void testSearch() {
        // TODO: Implement it in CustomMockContentProvider.
    }

    @Test
    public void testDelete() {
        providerInfo.reset();
        List<QueryView.SelectionOption> options = Collections.singletonList(
                new QueryView.SelectionOption(
                        MediaContentField.FieldName.ARTIST,
                        "Iron Maiden",
                        QueryView.SelectionOption.SelectionType.EQUALS)
        );
        ResultType result = handler.delete(
                ContentType.MainType.AUDIO, ContentType.SubType.TRACK, options);
        Assert.assertTrue(result.equals(ResultType.SUCCESS));
    }

    @Test
    public void testQueryAndDelete() {
        QueryView queryView = new QueryView(
                ContentType.MainType.AUDIO,
                ContentType.SubType.TRACK,
                MediaContentField.FieldName.TITLE, MediaContentField.FieldName.ALBUM);
        List<IMediaContentView> tracks = handler.query(queryView);
        handler.delete(tracks);
        Assert.assertTrue(tracks.size() == providerInfo.getTotalTracks());

        tracks = handler.query(queryView);
        Assert.assertTrue(tracks.size() == 0);
    }

    @Test
    public void testUpdate() {

    }
}
