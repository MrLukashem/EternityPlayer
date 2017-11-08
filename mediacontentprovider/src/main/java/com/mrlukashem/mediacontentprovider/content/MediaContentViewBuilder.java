package com.mrlukashem.mediacontentprovider.content;

import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MediaContentViewBuilder {
  private Map<MediaContentField, String> initialFields = new HashMap<>();
  private ContentType contentTypeToBuild;

  public MediaContentViewBuilder(@NotNull ContentType contentTypeToBuild) {
    this.contentTypeToBuild = contentTypeToBuild;
  }

  public IMediaContentView build() {
    return new MediaContentView(initialFields, contentTypeToBuild);
  }

  public MediaContentViewBuilder addField(@NotNull MediaContentField name, @NotNull String value) {
    initialFields.put(name, value);
    return this;
  }
}
