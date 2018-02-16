package com.mrlukashem.mediacontentprovider.mocks;

import android.content.ContentProvider;

import java.util.List;

/**
 * Created by MrLukashem on 27.11.2017.
 */

public interface ProviderTestDataController {
    int getTotalTracks();
    int getTotalAlbums();
    ContentProvider getProvider();
    void reset();
}
