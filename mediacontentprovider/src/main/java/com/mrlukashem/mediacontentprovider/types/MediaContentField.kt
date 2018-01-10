package com.mrlukashem.mediacontentprovider.types

data class MediaContentField(var fieldName: FieldName? = null, var fieldValue: String? = null) {
    enum class FieldName {
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
}
