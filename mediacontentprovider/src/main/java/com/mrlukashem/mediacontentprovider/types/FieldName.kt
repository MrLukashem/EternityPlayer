package com.mrlukashem.mediacontentprovider.types

/**
 * Created by MrLukashem on 15.02.2018.
 */
enum class FieldName {
    ID,
    // Fields for albums
    ALBUM,
    ALBUM_ART,
    ALBUM_ID,
    ALBUM_KEY,
    NUMBER_OF_SONGS,

    // Fields for artists
    ARTIST,
    ARTIST_ID,
    ARTIST_KEY,
    NUMBER_OF_ALBUMS,
    NUMBER_OF_TRACKS,

    // Fields for tracks
    BOOKMARK,
    DURATION,
    TRACK,
    TITLE_KEY,
    DATA,
    TITLE,
    MIME_TYPE
}