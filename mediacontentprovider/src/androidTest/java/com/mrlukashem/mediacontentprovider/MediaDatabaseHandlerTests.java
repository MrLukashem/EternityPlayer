package com.mrlukashem.mediacontentprovider;

import android.content.ContentProvider;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContentResolver;

import com.mrlukashem.mediacontentprovider.content.ContentView;
import com.mrlukashem.mediacontentprovider.data.MediaDatabaseHandler;
import com.mrlukashem.mediacontentprovider.data.QueryView;
import com.mrlukashem.mediacontentprovider.mocks.CustomMockContentProvider;
import com.mrlukashem.mediacontentprovider.mocks.ProviderTestDataController;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.FieldName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MediaDatabaseHandlerTests {
    private final ProviderTestDataController providerDataController =
            new CustomMockContentProvider(InstrumentationRegistry.getContext());
    private final MediaDatabaseHandler handler;

    public MediaDatabaseHandlerTests() {
        MockContentResolver resolver = new MockContentResolver();
        ContentProvider realProvider = providerDataController.getProvider();

        resolver.addProvider(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.getAuthority(), realProvider);
        handler = new MediaDatabaseHandler(resolver);
    }

    @Before
    public void setupTestsEnv() {
        providerDataController.reset();
    }

    @Test
    public void testQuery() {
        final int PROJECTION_SIZE_WITH_HANDLE_Q1 = 3;
        final int PROJECTION_SIZE_WITH_HANDLE_Q2 = 4;
        QueryView queryView = new QueryView(ContentType.TRACK, FieldName.TITLE, FieldName.ALBUM);
        List<ContentView> tracks = handler.query(queryView);
        Assert.assertTrue(tracks.size() == providerDataController.getTotalTracks());
        tracks.forEach(view -> Assert.assertTrue(
                view.getContentFields().size() == PROJECTION_SIZE_WITH_HANDLE_Q1));
        providerDataController.reset();

        queryView = new QueryView(
                ContentType.TRACK, FieldName.TITLE, FieldName.ALBUM, FieldName.ARTIST);
        tracks = handler.query(queryView);
        Assert.assertTrue(tracks.size() == providerDataController.getTotalTracks());
        tracks.forEach(view -> Assert.assertTrue(
                view.getContentFields().size() == PROJECTION_SIZE_WITH_HANDLE_Q2));
    }

    @Test
    public void testSearch() {
        final String trackTitle = "Another Life";
        List<ContentView> tracks = handler.search(trackTitle);
        Assert.assertFalse(tracks.isEmpty());
    }
/*
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
        List<ContentView> tracks = handler.query(queryView);
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
        List<ContentView> allTracks = handler.query(queryView);
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
        List<ContentView> tracksWithSelection = handler.query(queryView);
        Assert.assertTrue(tracksWithSelection.size() == 0);

        ContentView firstTrack = allTracks.get(0);
        ContentView updatedTrack = MediaContentView.BuilderFactory.from(firstTrack)
                .setField(FieldName.TITLE, "None").build();
        ResultType result = handler.update(Collections.singletonList(updatedTrack));
        Assert.assertTrue(result.equals(ResultType.SUCCESS));

        tracksWithSelection = handler.query(queryView);
        Assert.assertTrue(tracksWithSelection.size() == 0);
    }*/
}
