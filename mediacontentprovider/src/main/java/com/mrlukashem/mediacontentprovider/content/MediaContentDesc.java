package com.mrlukashem.mediacontentprovider.content;

import com.mrlukashem.mediacontentprovider.types.MediaContentDescField;
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MediaContentDesc implements IMediaContentDesc {
  private int contentKey;
  private MediaContentDescType contentType;
  private Map<MediaContentDescField, String> contentFields;

  private void initializeKey(@NotNull Map<MediaContentDescField, String> initialFields) {
    StringBuilder baseString = new StringBuilder(contentType.toString());
    for (String value : initialFields.values()) {
      baseString.append(value);
    }

    contentKey = baseString.toString().hashCode();
  }

  MediaContentDesc(@NotNull Map<MediaContentDescField, String> initialFields, @NotNull MediaContentDescType contentType) {
    this.contentFields = initialFields;
    this.contentType = contentType;
    initializeKey(initialFields);
  }

  @NotNull
  @Override
  public MediaContentDescType getType() {
    return contentType;
  }

  @NotNull
  @Override
  public String getField(@NotNull MediaContentDescField fieldName) {
    String fieldValue = contentFields.get(fieldName);
    return fieldValue != null ? fieldValue : "";
  }

  @Override
  public int getKey() {
    return contentKey;
  }
}
