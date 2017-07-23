package com.mrlukashem.mediacontentprovider.content;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MediaContentDesc implements IMediaContentDesc {
  private int _contentKey;
  private Type _contentType;
  private Map<Field, String> _contentFields;

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
    StringBuilder baseString = new StringBuilder(_contentType.toString());
    for (String value : initialFields.values()) {
      baseString.append(value);
    }

    _contentKey = baseString.toString().hashCode();
  }

  MediaContentDesc(@NotNull Map<Field, String> initialFields, @NotNull Type contentType) {
    _contentFields = initialFields;
    _contentType = contentType;
    initializeKey(initialFields);
  }

  @NotNull
  @Override
  public Type getType() {
    return null;
  }

  @NotNull
  @Override
  public String getField(@NotNull Field fieldName) {
    return null;
  }

  @Override
  public int getKey() {
    return _contentKey;
  }
}
