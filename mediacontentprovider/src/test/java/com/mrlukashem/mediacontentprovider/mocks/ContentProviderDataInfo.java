package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentProvider;

/**
 * Created by MrLukashem on 27.11.2017.
 */

public interface ContentProviderDataInfo {
    int getTotalTracks();
    int getTotalAlbums();
    ContentProvider getProvider();
    void reset();
}
