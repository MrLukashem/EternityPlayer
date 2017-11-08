package com.mrlukashem.mediacontentprovider.content;

import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MediaContentView implements IMediaContentView {
  private int contentKey;
  private ContentType contentType;
  private Map<MediaContentField, String> contentFields;

  private void initializeKey(@NotNull Map<MediaContentField, String> initialFields) {
    StringBuilder baseString = new StringBuilder(contentType.toString());
    for (String value : initialFields.values()) {
      baseString.append(value);
    }

    contentKey = baseString.toString().hashCode();
  }

  MediaContentView(@NotNull Map<MediaContentField, String> initialFields,
                   @NotNull ContentType contentType) {
    this.contentFields = initialFields;
    this.contentType = contentType;
    initializeKey(initialFields);
  }

  @NotNull
  @Override
  public ContentType getType() {
    return contentType;
  }

  @NotNull
  @Override
  public String getField(@NotNull MediaContentField fieldName) {
    String fieldValue = contentFields.get(fieldName);
    return fieldValue != null ? fieldValue : "";
  }

  @Override
  public int getKey() {
    return contentKey;
  }
}
