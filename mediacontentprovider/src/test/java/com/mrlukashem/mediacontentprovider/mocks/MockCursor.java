package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class MockCursor implements Cursor {
  private static final String ILLEGAL_STATE_EXCEPTION_MSG =
          "Wrong the Cursor state. Please check the position of Cursor.";
  private static final String ILLEGAL_ARGUMENT_EXCEPTION_MSG =
          "Passed column index does not exist.";
  private static final String NO_INDEX_FOUND_MSG =
          "No index found.";
  private static final int BEFORE_FIRST = -1;
  private static final int ERROR_CODE = -1;

  private List<ContentValues> contentValues;
  private String[] projection;
  private int count = 0;
  private int position = BEFORE_FIRST;
  // Mock closed.
  private boolean isClosed = false;

  private boolean updatePosition(int newValue, int defaultValue,
                                 BiPredicate<Integer, Integer> predicate) {
    boolean succeedMove =  predicate.test(newValue, count);
    position = succeedMove ? newValue : defaultValue;

    return succeedMove;
  }

  private void checkStake() throws IllegalStateException {
    if (isBeforeFirst() || isAfterLast()) {
      throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION_MSG);
    }
  }

  MockCursor(@NonNull String[] projection, @NonNull List<ContentValues> contentValues) {
    this.contentValues = contentValues;
    this.projection = projection;
    this.count = this.contentValues.size();
  }

  @Override
  public int getCount() {
    return count;
  }

  @Override
  public int getPosition() {
    return position;
  }

  @Override
  public boolean move(int i) {
    throw new NotImplementedException();
  }

  @Override
  public boolean moveToPosition(int i) {
    return updatePosition(i, position, (a, b) -> a < b && a >= 0);
  }

  @Override
  public boolean moveToFirst() {
    position = 0;
    return true;
  }

  @Override
  public boolean moveToLast() {
    position = count - 1;
    return true;
  }

  @Override
  public boolean moveToNext() {
    return updatePosition(position + 1, position, (a, b) -> a < b);
  }

  @Override
  public boolean moveToPrevious() {
    return updatePosition(position - 1, position, (a, b) -> a >= 0);
  }

  @Override
  public boolean isFirst() {
    return position == 0;
  }

  @Override
  public boolean isLast() {
    return position == count - 1;
  }

  @Override
  public boolean isBeforeFirst() {
    return position == BEFORE_FIRST;
  }

  @Override
  public boolean isAfterLast() {
    throw new NotImplementedException();
  }

  @Override
  public int getColumnIndex(String s) {
    for (int i = 0; i < projection.length; i++) {
      if (projection[i].equals(s)) {
        return i;
      }
    }

    return ERROR_CODE;
  }

  @Override
  public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
    int idx = getColumnIndex(s);
    if (idx != ERROR_CODE) return idx;

    throw new IllegalArgumentException(NO_INDEX_FOUND_MSG);
  }

  @Override
  public String getColumnName(int i) {
    return i >= 0 && i < projection.length ? projection[i] : "";
  }

  @Override
  public String[] getColumnNames() {
    return Arrays.copyOfRange(projection, 0, projection.length);
  }

  @Override
  public int getColumnCount() {
    return projection.length;
  }

  @Override
  public byte[] getBlob(int i) {
    throw new NotImplementedException();
  }

  @Override
  public String getString(int i) throws IllegalArgumentException, IllegalStateException {
    return getValue(i, String.class);
  }

  private <T> T getValue(int i, Class<T> clazz)
          throws IllegalArgumentException, IllegalStateException {
    try {
      checkStake();
      ContentValues currentRow = contentValues.get(position);
      return clazz.cast(currentRow.get(projection[i]));
    } catch (Exception exc) {
      throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MSG);
    }
  }

  @Override
  public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
    throw new NotImplementedException();
  }

  @Override
  public short getShort(int i) throws IllegalArgumentException, IllegalStateException {
    return getValue(i, Short.class);
  }

  @Override
  public int getInt(int i) throws IllegalArgumentException, IllegalStateException {
    return getValue(i, Integer.class);
  }

  @Override
  public long getLong(int i) throws IllegalArgumentException, IllegalStateException {
    return getValue(i, Long.class);
  }

  @Override
  public float getFloat(int i) throws IllegalArgumentException, IllegalStateException {
    return getValue(i, Float.class);
  }

  @Override
  public double getDouble(int i) throws IllegalArgumentException, IllegalStateException {
    return getValue(i, Double.class);
  }

  @Override
  public int getType(int i) {
    throw new NotImplementedException();
  }

  @Override
  public boolean isNull(int i) {
    throw new NotImplementedException();
  }

  @Override
  public void deactivate() {
    throw new NotImplementedException();
  }

  @Override
  public boolean requery() {
    throw new NotImplementedException();
  }

  @Override
  public void close() {
    isClosed = true;
  }

  @Override
  public boolean isClosed() {
    return isClosed;
  }

  @Override
  public void registerContentObserver(ContentObserver contentObserver) {
    throw new NotImplementedException();
  }

  @Override
  public void unregisterContentObserver(ContentObserver contentObserver) {
    throw new NotImplementedException();
  }

  @Override
  public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    throw new NotImplementedException();
  }

  @Override
  public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    throw new NotImplementedException();
  }

  @Override
  public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
    throw new NotImplementedException();
  }

  @Override
  public Uri getNotificationUri() {
    throw new NotImplementedException();
  }

  @Override
  public boolean getWantsAllOnMoveCalls() {
    throw new NotImplementedException();
  }

  @Override
  public void setExtras(Bundle bundle) {
    throw new NotImplementedException();
  }

  @Override
  public Bundle getExtras() {
    throw new NotImplementedException();
  }

  @Override
  public Bundle respond(Bundle bundle) {
    throw new NotImplementedException();
  }
}
