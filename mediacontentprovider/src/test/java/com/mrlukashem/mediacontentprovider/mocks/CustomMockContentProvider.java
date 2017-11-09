package com.mrlukashem.mediacontentprovider.mocks;

import android.database.Cursor;
import android.net.Uri;
import android.test.mock.MockContentProvider;

@Mock
public class CustomMockContentProvider extends MockContentProvider {

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    return null;
  }
}
