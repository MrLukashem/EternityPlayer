package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import com.mrlukashem.mediacontentprovider.content.IMediaContentView;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.jetbrains.annotations.NotNull;

public class MockCursor implements Cursor {
  private static final int BEFORE_FIRST = -1;

  private Map<MediaContentField.FieldName, Integer> fieldToColumn =
          new HashMap<>();

  private List<IMediaContentView> contentViews;
  private int count = 0;
  private int position = BEFORE_FIRST;

  private void initFieldToColumnMap() {
  }

  private boolean updatePosition(int newValue, int defaultValue,
                                 BiPredicate<Integer, Integer> predicate) {
    boolean succeedMove =  predicate.test(newValue, count);
    position = succeedMove ? newValue : defaultValue;

    return succeedMove;
  }

  MockCursor(@NotNull List<IMediaContentView> contentViews) {
    this.contentViews = contentViews;
    this.count = contentViews.size();

    initFieldToColumnMap();
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
    throw new NotImplementedException();
  }

  @Override
  public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
    throw new NotImplementedException();
  }

  @Override
  public String getColumnName(int i) {
    throw new NotImplementedException();
  }

  @Override
  public String[] getColumnNames() {
    throw new NotImplementedException();
  }

  @Override
  public int getColumnCount() {
    throw new NotImplementedException();
  }

  @Override
  public byte[] getBlob(int i) {
    throw new NotImplementedException();
  }

  @Override
  public String getString(int i) {
    throw new NotImplementedException();
  }

  @Override
  public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
    throw new NotImplementedException();
  }

  @Override
  public short getShort(int i) {
    throw new NotImplementedException();
  }

  @Override
  public int getInt(int i) {
    throw new NotImplementedException();
  }

  @Override
  public long getLong(int i) {
    throw new NotImplementedException();
  }

  @Override
  public float getFloat(int i) {
    throw new NotImplementedException();
  }

  @Override
  public double getDouble(int i) {
    throw new NotImplementedException();
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
    throw new NotImplementedException();
  }

  @Override
  public boolean isClosed() {
    throw new NotImplementedException();
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
