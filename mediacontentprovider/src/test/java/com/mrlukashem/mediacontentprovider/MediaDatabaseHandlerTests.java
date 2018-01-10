package com.mrlukashem.mediacontentprovider;

import android.test.mock.MockContentResolver;

import com.mrlukashem.mediacontentprovider.content.IMediaContentView;
import com.mrlukashem.mediacontentprovider.content.MediaContentView;
import com.mrlukashem.mediacontentprovider.data.DataHandler.*;
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler;
import com.mrlukashem.mediacontentprovider.data.QueryView;
import com.mrlukashem.mediacontentprovider.data.QueryView.SelectionOption;
import com.mrlukashem.mediacontentprovider.mocks.CustomMockContentProvider;
import com.mrlukashem.mediacontentprovider.mocks.ProviderTestDataController;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class MediaDatabaseHandlerTests {
    private final ProviderTestDataController providerDataController = new CustomMockContentProvider();
    private final MediaDatabaseHandler handler;

    public MediaDatabaseHandlerTests() {
        MockContentResolver resolver = new MockContentResolver();
        resolver.addProvider("MockProvider", providerDataController.getProvider());
        handler = new MediaDatabaseHandler(resolver);
    }

    @Before
    public void setupTestsEnv() {
        providerDataController.reset();
    }

    @Test
    public void testQuery() {
        QueryView queryView = new QueryView(
                ContentType.MainType.AUDIO,
                ContentType.SubType.TRACK,
                FieldName.TITLE, FieldName.ALBUM);
        List<IMediaContentView> tracks = handler.query(queryView);
        Assert.assertTrue(tracks.size() == providerDataController.getTotalTracks());
    }

    @Test
    public void testSearch() {
        List<IMediaContentView> tracks = handler.search(Collections.singletonList("Another Life"));
        Assert.assertFalse(tracks.isEmpty());
    }

    @Test
    public void testDelete() {
        List<QueryView.SelectionOption> options = Collections.singletonList(
                new QueryView.SelectionOption(
                        FieldName.ARTIST,
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
                FieldName.TITLE, FieldName.ALBUM);
        List<IMediaContentView> tracks = handler.query(queryView);
        handler.delete(tracks);
        Assert.assertTrue(tracks.size() == providerDataController.getTotalTracks());

        tracks = handler.query(queryView);
        Assert.assertTrue(tracks.size() == 0);
    }

    @Test
    public void testUpdate() {
        QueryView queryView = new QueryView(
                ContentType.MainType.AUDIO,
                ContentType.SubType.TRACK,
                FieldName.TITLE, FieldName.ALBUM);
        List<IMediaContentView> allTracks = handler.query(queryView);
        Assert.assertTrue(allTracks.size() == providerDataController.getTotalTracks());

        queryView = QueryView.QueryBuilder.create()
                .setContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
                .setFieldProjection(FieldName.TITLE)
                .setFieldProjection(FieldName.ALBUM)
                .setSelectionOption(new SelectionOption(
                        FieldName.TITLE,
                        "None",
                        SelectionOption.SelectionType.EQUALS))
                .build();
        List<IMediaContentView> tracksWithSelection = handler.query(queryView);
        Assert.assertTrue(tracksWithSelection.size() == 0);

        IMediaContentView firstTrack = allTracks.get(0);
        IMediaContentView updatedTrack = MediaContentView.ContentBuilder.from(firstTrack)
                .contentField(FieldName.TITLE, "None").build();
        ResultType result = handler.update(updatedTrack);
        Assert.assertTrue(result.equals(ResultType.SUCCESS));

        tracksWithSelection = handler.query(queryView);
        Assert.assertTrue(tracksWithSelection.size() == 0);
    }
}
