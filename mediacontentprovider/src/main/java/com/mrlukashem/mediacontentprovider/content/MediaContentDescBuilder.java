package com.mrlukashem.mediacontentprovider.content;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MediaContentDescBuilder {
  private Map<MediaContentDesc.Field, String> initialFields = new HashMap<>();
  private MediaContentDesc.Type contentTypeToBuild;

  public MediaContentDescBuilder(@NotNull MediaContentDesc.Type contentTypeToBuild) {
    this.contentTypeToBuild = contentTypeToBuild;
  }

  public IMediaContentDesc build() {
    return new MediaContentDesc(initialFields, contentTypeToBuild);
  }

  public MediaContentDescBuilder addField(@NotNull MediaContentDesc.Field name, @NotNull String value) {
    initialFields.put(name, value);
    return this;
  }
}
