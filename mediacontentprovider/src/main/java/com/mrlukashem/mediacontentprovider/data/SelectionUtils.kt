package com.mrlukashem.mediacontentprovider.data

/**
 * Created by MrLukashem on 16.02.2018.
 */

class SelectionConstants {
    companion object {
        const val EQUALS = "E"
        const val EQUALS_GREATER = "EG"
        const val EQUALS_LESS = "EL"
        const val LESS = "L"
        const val GREATER = "G"
    }
}

object ID
// Fields for albums
object ALBUM
object ALBUM_ART
object ALBUM_ID
object ALBUM_KEY
object NUMBER_OF_SONGS

// Fields for artists
object ARTIST
object ARTIST_ID
object ARTIST_KEY
object NUMBER_OF_ALBUMS
object NUMBER_OF_TRACKS

// Fields for tracks
object BOOKMARK
object DURATION
object TRACK
object TITLE_KEY
object DATA
object TITLE
object MIME_TYPE

infix fun ID.equals(value: String) = "ID:E:$value"
infix fun ID.equalsGreater(value: String) = "ID:EG:$value"
infix fun ID.equalsLess(value: String) = "ID:EL:$value"
infix fun ID.less(value: String) = "ID:L:$value"
infix fun ID.greater(value: String) = "ID:G:$value"

infix fun ALBUM.equals(value: String) = "ALBUM:E:$value"
infix fun ALBUM.equalsGreater(value: String) = "ALBUM:EG:$value"
infix fun ALBUM.equalsLess(value: String) = "ALBUM:EL:$value"
infix fun ALBUM.less(value: String) = "ALBUM:L:$value"
infix fun ALBUM.greater(value: String) = "ALBUM:G:$value"

infix fun ARTIST.equals(value: String) = "ARTIST:E:$value"
infix fun ARTIST.equalsGreater(value: String) = "ARTIST:EG:$value"
infix fun ARTIST.equalsLess(value: String) = "ARTIST:EL:$value"
infix fun ARTIST.less(value: String) = "ARTIST:L:$value"
infix fun ARTIST.greater(value: String) = "ARTIST:G:$value"

infix fun TRACK.equals(value: String) = "TRACK:E:$value"
infix fun TRACK.equalsGreater(value: String) = "TRACK:EG:$value"
infix fun TRACK.equalsLess(value: String) = "TRACK:EL:$value"
infix fun TRACK.less(value: String) = "TRACK:L:$value"
infix fun TRACK.greater(value: String) = "TRACK:G:$value"

infix fun ALBUM_ART.equals(value: String) = "ALBUM_ART:E:$value"
infix fun ALBUM_ART.equalsGreater(value: String) = "ALBUM_ART:EG:$value"
infix fun ALBUM_ART.equalsLess(value: String) = "ALBUM_ART:EL:$value"
infix fun ALBUM_ART.less(value: String) = "ALBUM_ART:L:$value"
infix fun ALBUM_ART.greater(value: String) = "ALBUM_ART:G:$value"

infix fun ALBUM_ID.equals(value: String) = "ALBUM_ID:E:$value"
infix fun ALBUM_ID.equalsGreater(value: String) = "ALBUM_ID:EG:$value"
infix fun ALBUM_ID.equalsLess(value: String) = "ALBUM_ID:EL:$value"
infix fun ALBUM_ID.less(value: String) = "ALBUM_ID:L:$value"
infix fun ALBUM_ID.greater(value: String) = "ALBUM_ID:G:$value"

infix fun ALBUM_KEY.equals(value: String) = "ALBUM_KEY:E:$value"
infix fun ALBUM_KEY.equalsGreater(value: String) = "ALBUM_KEY:EG:$value"
infix fun ALBUM_KEY.equalsLess(value: String) = "ALBUM_KEY:EL:$value"
infix fun ALBUM_KEY.less(value: String) = "ALBUM_KEY:L:$value"
infix fun ALBUM_KEY.greater(value: String) = "ALBUM_KEY:G:$value"

infix fun NUMBER_OF_SONGS.equals(value: String) = "NUMBER_OF_SONGS:E:$value"
infix fun NUMBER_OF_SONGS.equalsGreater(value: String) = "NUMBER_OF_SONGS:EG:$value"
infix fun NUMBER_OF_SONGS.equalsLess(value: String) = "NUMBER_OF_SONGS:EL:$value"
infix fun NUMBER_OF_SONGS.less(value: String) = "NUMBER_OF_SONGS:L:$value"
infix fun NUMBER_OF_SONGS.greater(value: String) = "NUMBER_OF_SONGS:G:$value"

infix fun ARTIST_ID.equals(value: String) = "ARTIST_ID:E:$value"
infix fun ARTIST_ID.equalsGreater(value: String) = "ARTIST_ID:EG:$value"
infix fun ARTIST_ID.equalsLess(value: String) = "ARTIST_ID:EL:$value"
infix fun ARTIST_ID.less(value: String) = "ARTIST_ID:L:$value"
infix fun ARTIST_ID.greater(value: String) = "ARTIST_ID:G:$value"

infix fun ARTIST_KEY.equals(value: String) = "ARTIST_KEY:E:$value"
infix fun ARTIST_KEY.equalsGreater(value: String) = "ARTIST_KEY:EG:$value"
infix fun ARTIST_KEY.equalsLess(value: String) = "ARTIST_KEY:EL:$value"
infix fun ARTIST_KEY.less(value: String) = "ARTIST_KEY:L:$value"
infix fun ARTIST_KEY.greater(value: String) = "ARTIST_KEY:G:$value"

infix fun NUMBER_OF_ALBUMS.equals(value: String) = "NUMBER_OF_ALBUMS:E:$value"
infix fun NUMBER_OF_ALBUMS.equalsGreater(value: String) = "NUMBER_OF_ALBUMS:EG:$value"
infix fun NUMBER_OF_ALBUMS.equalsLess(value: String) = "NUMBER_OF_ALBUMS:EL:$value"
infix fun NUMBER_OF_ALBUMS.less(value: String) = "NUMBER_OF_ALBUMS:L:$value"
infix fun NUMBER_OF_ALBUMS.greater(value: String) = "NUMBER_OF_ALBUMS:G:$value"

infix fun NUMBER_OF_TRACKS.equals(value: String) = "NUMBER_OF_TRACKS:E:$value"
infix fun NUMBER_OF_TRACKS.equalsGreater(value: String) = "NUMBER_OF_TRACKS:EG:$value"
infix fun NUMBER_OF_TRACKS.equalsLess(value: String) = "NUMBER_OF_TRACKS:EL:$value"
infix fun NUMBER_OF_TRACKS.less(value: String) = "NUMBER_OF_TRACKS:L:$value"
infix fun NUMBER_OF_TRACKS.greater(value: String) = "NUMBER_OF_TRACKS:G:$value"

infix fun BOOKMARK.equals(value: String) = "BOOKMARK:E:$value"
infix fun BOOKMARK.equalsGreater(value: String) = "BOOKMARK:EG:$value"
infix fun BOOKMARK.equalsLess(value: String) = "BOOKMARK:EL:$value"
infix fun BOOKMARK.less(value: String) = "BOOKMARK:L:$value"
infix fun BOOKMARK.greater(value: String) = "BOOKMARK:G:$value"

infix fun DURATION.equals(value: String) = "DURATION:E:$value"
infix fun DURATION.equalsGreater(value: String) = "DURATION:EG:$value"
infix fun DURATION.equalsLess(value: String) = "DURATION:EL:$value"
infix fun DURATION.less(value: String) = "DURATION:L:$value"
infix fun DURATION.greater(value: String) = "DURATION:G:$value"

infix fun TITLE_KEY.equals(value: String) = "TITLE_KEY:E:$value"
infix fun TITLE_KEY.equalsGreater(value: String) = "TITLE_KEY:EG:$value"
infix fun TITLE_KEY.equalsLess(value: String) = "TITLE_KEY:EL:$value"
infix fun TITLE_KEY.less(value: String) = "TITLE_KEY:L:$value"
infix fun TITLE_KEY.greater(value: String) = "TITLE_KEY:G:$value"

infix fun DATA.equals(value: String) = "DATA:E:$value"
infix fun DATA.equalsGreater(value: String) = "DATA:EG:$value"
infix fun DATA.equalsLess(value: String) = "DATA:EL:$value"
infix fun DATA.less(value: String) = "DATA:L:$value"
infix fun DATA.greater(value: String) = "DATA:G:$value"

infix fun TITLE.equals(value: String) = "TITLE:E:$value"
infix fun TITLE.equalsGreater(value: String) = "TITLE:EG:$value"
infix fun TITLE.equalsLess(value: String) = "TITLE:EL:$value"
infix fun TITLE.less(value: String) = "TITLE:L:$value"
infix fun TITLE.greater(value: String) = "TITLE:G:$value"

infix fun MIME_TYPE.equals(value: String) = "MIME_TYPE:E:$value"
infix fun MIME_TYPE.equalsGreater(value: String) = "MIME_TYPE:EG:$value"
infix fun MIME_TYPE.equalsLess(value: String) = "MIME_TYPE:EL:$value"
infix fun MIME_TYPE.less(value: String) = "MIME_TYPE:L:$value"
infix fun MIME_TYPE.greater(value: String) = "MIME_TYPE:G:$value"
