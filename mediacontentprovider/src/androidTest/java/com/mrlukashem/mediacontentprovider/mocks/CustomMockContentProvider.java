package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.test.mock.MockContentProvider;

import com.mrlukashem.mediacontentprovider.utils.ArrayStreamList;
import com.mrlukashem.mediacontentprovider.utils.Optional;
import com.mrlukashem.mediacontentprovider.utils.StreamList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomMockContentProvider
        extends MockContentProvider implements ProviderTestDataController {
    private static final int TOTAL_TRACKS = 3;

    private StreamList<DataSet> dataSets = new ArrayStreamList<>();
    private Cursor currentCursor;

    public CustomMockContentProvider(Context context) {
        super(context);
        initContentValues();
    }

    private class DataSet {
        StreamList<String> cols;
        StreamList<ContentValues> rows;

        DataSet(@NonNull StreamList<String> cols, @NonNull StreamList<ContentValues> rows) {
            this.cols = cols;
            this.rows = rows;
        }

        void shrinkTo(@NonNull String[] destRawCols) {
            List<String> destCols = Arrays.asList(destRawCols);
            if (cols.containsAll(destCols)) {
                removeRows(destCols);
                cols.retainAll(destCols);
            }
        }

        void removeRows(List<String> validCols) {
            List<String> toRemove = cols.getStream().collect();
            toRemove.removeAll(validCols);
            rows.forEach(item -> toRemove.forEach(item::remove));
        }

        Cursor createMockCursor() {
            String[] projections = new String[cols.size()];
            return new MockCursor(cols.toArray(projections), rows);
        }
    }

    private class SelectionPredicateFactory {
        Map<String, Predicate<String>> createPredicates(String selection, String[] selectionArgs) {
            String[] cols = unpackSelection(selection);
            Map<String, Predicate<String>> predicates = new HashMap<>();

            if (isSelectionValid(cols, selectionArgs) ) {
                for (int i = 0; i < selectionArgs.length; i++) {
                    final String selectionArg = selectionArgs[i];
                    predicates.put(cols[i], (colValue) -> colValue.equals(selectionArg));
                }
            }

            return predicates;
        }
    }

    private String[] unpackSelection(String selection) {
        String[] cols = {};
        return selection != null ? selection.split(".+//s*=//s*//?]") : cols;
    }

    private boolean isSelectionValid(String[] unpackedSelection, String[] selectionArgs) {
        return (unpackedSelection != null && selectionArgs != null)
                && (unpackedSelection.length == selectionArgs.length);
    }

    // TODO: Temporary hard-coded. It should be loaded from a file.
    private void initDataSetKillers() {
        dataSets.clear();
        String[] cols = new String[] {
                "artist",
                "_data",
                "album",
                "title",
                "mime_type",
                "size",
        };
        StreamList<ContentValues> rows = new ArrayStreamList<>();
        ContentValues tempContentValues = new ContentValues();

        tempContentValues.put("artist", "Iron Maiden");
        tempContentValues.put("_data", "/sdcard/external/audio/Killers.mp3");
        tempContentValues.put("album", "Killers");
        tempContentValues.put("title", "Killers");
        tempContentValues.put("mime_type", "audio/mpeg");
        tempContentValues.put("size", (long)(666));
        rows.add(tempContentValues);

        tempContentValues = new ContentValues();
        tempContentValues.put("artist", "Iron Maiden");
        tempContentValues.put("_data", "/sdcard/external/audio/Wrathchild.mp3");
        tempContentValues.put("album", "Killers");
        tempContentValues.put("title", "Wrathchild");
        tempContentValues.put("mime_type", "audio/mpeg");
        tempContentValues.put("size", (long)(432));
        rows.add(tempContentValues);

        tempContentValues = new ContentValues();
        tempContentValues.put("artist", "Iron Maiden");
        tempContentValues.put("_data", "/sdcard/external/audio/Another Life.mp3");
        tempContentValues.put("album", "Killers");
        tempContentValues.put("title", "Another Life");
        tempContentValues.put("mime_type", "audio/mpeg");
        tempContentValues.put("size", (long)(100043));
        rows.add(tempContentValues);

        dataSets.add(new DataSet(
                new ArrayStreamList<>(Arrays.asList(cols)),
                rows));
        }

    private void initContentValues() {
        initDataSetKillers();
    }

    @Override
    public Cursor query(
            Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        if (isFancySearch(uri)) {
            return processFancySearch(uri);
        }

        if (selection != null && selectionArgs != null) {
            return processQueryWithSelection(uri, selection, selectionArgs);
        }

        Optional<DataSet> dataSetResult = dataSets.getStream().filter(
                dataSet -> dataSet.cols.containsAll(Arrays.asList(projection))).findFirst();

        Cursor cursor = null;
        if (dataSetResult.isPresent()) {
            dataSetResult.get().shrinkTo(projection);
            final SelectionPredicateFactory predicateFactory = new SelectionPredicateFactory();
            final Map<String, Predicate<String>> predicates =
                    predicateFactory.createPredicates(selection, selectionArgs);
            StreamList<ContentValues> rowsAfterSelection = new ArrayStreamList<>();

            Function<ContentValues, Boolean> isValidWithSelection = (contentValues) -> {
                boolean isValid = true;
                for (String col : dataSetResult.get().cols) {
                    Predicate<String> predicate = predicates.get(col);
                    String value = contentValues.getAsString(col);
                    isValid = predicate == null || isValid && predicate.test(value);
                }

                return isValid;
            };

            for (ContentValues contentValues : dataSetResult.get().rows) {
                if (isValidWithSelection.apply(contentValues)) {
                    rowsAfterSelection.add(contentValues);
                }
            }

            StreamList<String> sourceCols = dataSetResult.get().cols;
            String[] cols = sourceCols.toArray(new String[sourceCols.size()]);
            cursor = new MockCursor(cols, rowsAfterSelection);
        }

        return cursor;
    }

    private boolean isFancySearch(Uri uri) {
        int size = uri.getPathSegments().size();
        String search = uri.getPathSegments().get(size - 3);
        String fancy = uri.getPathSegments().get(size - 2);

        return search.equals("search") && fancy.equals("fancy");
    }

    private Cursor processFancySearch(Uri uri) {
        int size = uri.getPathSegments().size();
        String audio = uri.getPathSegments().get(size - 4);
        if (!audio.equals("audio")) {
            return new MockCursor(new String[] {}, Collections.emptyList());
        }

        DataSet dataSet = dataSets.get(0);
        StreamList<String> sourceCols = dataSet.cols;
        String[] cols = sourceCols.toArray(new String[sourceCols.size()]);
        return new MockCursor(cols, Collections.singletonList(dataSet.rows.get(0)));
    }

    private Cursor processQueryWithSelection(Uri uri, String selection, String[] selectionArgs) {
        return null;
    }

    @Override
    public int getTotalTracks() {
        return TOTAL_TRACKS;
    }

    @Override
    public int getTotalAlbums() {
        // TODO: Temporary hardcoded because we have only an album - The Killers.
        return 1;
    }

    @Override
    public ContentProvider getProvider() {
        return this;
    }

    @Override
    public void reset() {
        initContentValues();
    }
}
