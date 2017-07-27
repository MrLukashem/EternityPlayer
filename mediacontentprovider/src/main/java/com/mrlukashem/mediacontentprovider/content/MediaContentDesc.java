package com.mrlukashem.mediacontentprovider.content;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MediaContentDesc implements IMediaContentDesc {
  private int contentKey;
  private Type contentType;
  private Map<Field, String> contentFields;

  public enum Type {
    ALBUM,
    ARTIST,
    PLAYLIST,
    TRACK,
  }

  public enum Field {
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

  private void initializeKey(@NotNull Map<Field, String> initialFields) {
    StringBuilder baseString = new StringBuilder(contentType.toString());
    for (String value : initialFields.values()) {
      baseString.append(value);
    }

    contentKey = baseString.toString().hashCode();
  }

  MediaContentDesc(@NotNull Map<Field, String> initialFields, @NotNull Type contentType) {
    this.contentFields = initialFields;
    this.contentType = contentType;
    initializeKey(initialFields);
  }

  @NotNull
  @Override
  public Type getType() {
    return contentType;
  }

  @NotNull
  @Override
  public String getField(@NotNull Field fieldName) {
    String fieldValue = contentFields.get(fieldName);
    return fieldValue != null ? fieldValue : "";
  }

  @Override
  public int getKey() {
    return contentKey;
  }
}
