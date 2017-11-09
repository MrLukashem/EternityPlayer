package com.mrlukashem.mediacontentprovider.types;

import org.jetbrains.annotations.NotNull;

public class MediaContentField {
  public FieldName fieldName;
  public String fieldValue;

  public enum FieldName {
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
    MIME_TYPE,
  }

  public MediaContentField(@NotNull FieldName field, @NotNull String fieldValue) {
    this.fieldName = field;
    this.fieldValue = fieldValue;
  }
}
