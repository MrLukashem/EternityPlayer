package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.test.mock.MockContentProvider;

import com.mrlukashem.mediacontentprovider.data.QueryView;
import com.mrlukashem.mediacontentprovider.utils.ArrayStreamList;
import com.mrlukashem.mediacontentprovider.utils.Optional;
import com.mrlukashem.mediacontentprovider.utils.StreamList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomMockContentProvider
        extends MockContentProvider implements ProviderTestDataController {
    private static final int TOTAL_TRACKS = 3;
    private static final int DEFAULT_DATA_SET_INDEX = 0;

    private StreamList<DataSet> dataSets = new ArrayStreamList<>();
    private QueryStrategyFactory queryStrategyFactory = new QueryStrategyFactory();

    public CustomMockContentProvider(Context context) {
        super(context);
        initContentValues();
    }

    private void initContentValues() {
        initDataSetKillers();
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

    @Override
    public Cursor query(
            Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        QueryStrategy queryStrategy =
                queryStrategyFactory.create(uri, projection, selection, selectionArgs, sortOrder);
        return queryStrategy.internalQuery();
    }

    private interface QueryStrategy {
        Cursor internalQuery();
    }

    private class QueryStrategyFactory {
        QueryStrategy create(Uri uri, String[] projection, String selection,
                             String[] selectionArgs, String sortOrder) {
            if (isFancySearch(uri)) {
                return new FancySearchQuery(uri);
            } else if (isProjectionQuery(uri, projection, selection)) {
                return new ProjectionQuery(projection);
            } else if (isSelectionQuery(selection, selectionArgs)){
                return new SelectionQuery(projection, selection, selectionArgs);
            } else {
                return new EmptyQueryStrategy();
            }
        }

        private boolean isFancySearch(Uri uri) {
            int size = uri.getPathSegments().size();
            String search = uri.getPathSegments().get(size - 3);
            String fancy = uri.getPathSegments().get(size - 2);

            return search.equals("search") && fancy.equals("fancy");
        }

        private boolean isProjectionQuery(Uri uri, String[] projection, String selection) {
            return uri != null && projection != null && selection == null;
        }

        private boolean isSelectionQuery(String selection, String[] selectionArgs) {
            return selection != null && selectionArgs != null;
        }
    }

    private class FancySearchQuery implements QueryStrategy {
        private Uri ownUri;

        FancySearchQuery(Uri uri) {
            ownUri = uri;
        }

        @Override
        public Cursor internalQuery() {
            return processFancySearch(ownUri);
        }

        private Cursor processFancySearch(Uri uri) {
            int size = uri.getPathSegments().size();
            String audio = uri.getPathSegments().get(size - 4);
            if (!audio.equals("audio")) {
                return new MockCursor(new String[] {}, Collections.emptyList());
            }

            DataSet dataSet = dataSets.get(DEFAULT_DATA_SET_INDEX);
            StreamList<String> sourceCols = dataSet.cols;
            String[] cols = sourceCols.toArray(new String[sourceCols.size()]);
            return new MockCursor(cols, Collections.singletonList(dataSet.rows.get(0)));
        }
    }

    private class ProjectionQuery implements QueryStrategy {
        private String[] ownProjection;

        ProjectionQuery(String[] projection) {
            ownProjection = projection;
        }

        @Override
        public Cursor internalQuery() {
            Optional<DataSet> dataSetResult = dataSets.getStream().filter(
                    dataSet -> dataSet.cols.containsAll(Arrays.asList(ownProjection))).findFirst();

            Cursor cursor = null;
            if (dataSetResult.isPresent()) {
                dataSetResult.get().shrinkTo(ownProjection);

                StreamList<String> sourceCols = dataSetResult.get().cols;
                String[] cols = sourceCols.toArray(new String[sourceCols.size()]);
                cursor = new MockCursor(cols, dataSetResult.get().rows);
            }

            return cursor;
        }
    }

    private class SelectionQuery implements QueryStrategy {
        private String[] ownProjection;
        private String ownSelection;
        private String[] ownSelectionArgs;

        SelectionQuery(String[] projection, String selection, String[] selectionArgs) {
            ownProjection = projection;
            ownSelection = selection;
            ownSelectionArgs = selectionArgs;
        }

        @Override
        public Cursor internalQuery() {
            final SelectionPredicateFactory predicateFactory = new SelectionPredicateFactory();
            final Map<String, Predicate<String>> predicates =
                    predicateFactory.createPredicates(ownSelection, ownSelectionArgs);
            Optional<DataSet> dataSetResult = dataSets.getStream().filter(
                    dataSet -> dataSet.cols.containsAll(Arrays.asList(ownProjection))).findFirst();
            StreamList<ContentValues> rowsAfterSelection = new ArrayStreamList<>();

            return dataSetResult.ifPresentWithResult(dataSet -> {
                Function<ContentValues, Boolean> isValidWithSelection = (contentValues) -> {
                    boolean isValid = false;
                    for (String col : dataSetResult.get().cols) {
                        Predicate<String> predicate = predicates.get(col);
                        String value = contentValues.getAsString(col);
                        isValid = isValid || (predicate != null && predicate.test(value));
                    }

                    return isValid;
                };

                for (ContentValues row : dataSetResult.get().rows) {
                    if (isValidWithSelection.apply(row)) {
                        rowsAfterSelection.add(row);
                    }
                }

                return new MockCursor(ownProjection, rowsAfterSelection);
            });
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

        private String[] unpackSelection(String selection) {
            String[] noCols = {};
            return selection != null ? selection.split("\\s*=\\s*\\?(\\s+AND\\s+)*") : noCols;
        }

        private boolean isSelectionValid(String[] unpackedSelection, String[] selectionArgs) {
            return (unpackedSelection != null && selectionArgs != null)
                    && (unpackedSelection.length == selectionArgs.length);
        }
    }

    private static class EmptyQueryStrategy implements QueryStrategy {
        @Override
        public Cursor internalQuery() {
            return null;
        }
    }
}
