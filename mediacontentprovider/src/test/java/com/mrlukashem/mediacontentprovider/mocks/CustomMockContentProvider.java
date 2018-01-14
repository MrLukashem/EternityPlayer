package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.test.mock.MockContentProvider;

import com.mrlukashem.mediacontentprovider.utils.ArrayStreamList;
import com.mrlukashem.mediacontentprovider.utils.StreamList;
import com.mrlukashem.mediacontentprovider.utils.Optional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Mock
public class CustomMockContentProvider
        extends MockContentProvider implements ProviderTestDataController {
  private class DataSet {
    StreamList<String> cols;
    StreamList<ContentValues> rows;

    DataSet(@NonNull StreamList<String> cols, @NonNull StreamList<ContentValues> rows) {
      this.cols = cols;
      this.rows = rows;
    }

    void removeCol(@NonNull String col) {
      cols = cols.getStream().filter(item -> !item.equals(col)).collect();
      rows.forEach(item -> item.remove(col));
    }

    Cursor createMockCursor() {
      String[] projections = new String[cols.size()];
      return new MockCursor(cols.toArray(projections), rows);
    }
  }

  private class SelectionPredicateFactory {
    Map<String, Predicate<String>> createPredicates(
            @NonNull String selection, @NonNull String[] selectionArgs) {
      final String[] cols = selection.split(".+//s*=//s*//?]");
      Map<String, Predicate<String>> predicates = new HashMap<>();

      if (cols.length == selectionArgs.length) {
        for (int i = 0; i < selectionArgs.length; i++) {
          final String selectionArg = selectionArgs[i];
          predicates.put(cols[i], (colValue) -> colValue.equals(selectionArg));
        }
      }

      return predicates;
    }
  }

  private StreamList<DataSet> dataSets = new ArrayStreamList<>();
  private Cursor currentCursor;

  // TODO: Temporary hard-coded. It should be loaded from a file.
  private void initDataSetKillers() {
    dataSets.clear();
    String[] cols = new String[] {
            "ARTIST",
            "DATA",
            "ALBUM",
            "TITLE",
            "MIME_TYPE",
            "SIZE",
    };
    StreamList<ContentValues> rows = new ArrayStreamList<>();
    ContentValues tempContentValues = new ContentValues();

    tempContentValues.put("ARTIST", "Iron Maiden");
    tempContentValues.put("DATA", "NONE");
    tempContentValues.put("ALBUM", "Killers");
    tempContentValues.put("TITLE", "Killers");
    tempContentValues.put("MIME_TYPE", "audio/mpeg");
    tempContentValues.put("SIZE", (long)(666));
    rows.add(tempContentValues);

    tempContentValues = new ContentValues();
    tempContentValues.put("ARTIST", "Iron Maiden");
    tempContentValues.put("DATA", "NONE");
    tempContentValues.put("ALBUM", "Killers");
    tempContentValues.put("TITLE", "Wrathchild");
    tempContentValues.put("MIME_TYPE", "audio/mpeg");
    tempContentValues.put("SIZE", (long)(432));
    rows.add(tempContentValues);

    tempContentValues = new ContentValues();
    tempContentValues.put("ARTIST", "Iron Maiden");
    tempContentValues.put("DATA", "NONE");
    tempContentValues.put("ALBUM", "Killers");
    tempContentValues.put("TITLE", "Another Life");
    tempContentValues.put("MIME_TYPE", "audio/mpeg");
    tempContentValues.put("SIZE", (long)(100043));
    rows.add(tempContentValues);

    dataSets.add(new DataSet(
            new ArrayStreamList<>(Arrays.asList(cols)),
            rows));
  }

  private void initContentValues() {
    initDataSetKillers();
  }

  public CustomMockContentProvider() {
    super();
    initContentValues();
  }

  @Override
  public Cursor query(
          Uri uri, String[] projection, String selection, String[] selectionArgs,
          String sortOrder) {
    Optional<DataSet> dataSetResult = dataSets.getStream().filter(
            dataSet -> Arrays.asList(projection).equals(dataSet.cols)).findFirst();

    Cursor cursor = null;
    if (dataSetResult.isPresent()) {
      final SelectionPredicateFactory predicateFactory = new SelectionPredicateFactory();
      final Map<String, Predicate<String>> predicates =
              predicateFactory.createPredicates(selection, selectionArgs);
      StreamList<ContentValues> rowsAfterSelection = new ArrayStreamList<>();

      Function<ContentValues, Boolean> isValidWithSelection = (contentValues) -> {
        boolean isValid = true;
        for (String col : dataSetResult.get().cols) {
          Predicate<String> predicate = predicates.get(col);
          String value = contentValues.getAsString(col);
          isValid = isValid && predicate.test(value);
        }

        return isValid;
      };

      for (ContentValues contentValues : dataSetResult.get().rows) {
        if (isValidWithSelection.apply(contentValues)) {
          rowsAfterSelection.add(contentValues);
        }
      }

      cursor = new MockCursor((String[])dataSetResult.get().cols.toArray(), rowsAfterSelection);
    }

    return cursor;
  }

  @Override
  public int getTotalTracks() {
    return dataSets.size();
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
