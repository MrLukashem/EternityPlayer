package com.mrlukashem.mediacontentprovider.content;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MediaContentDescBuilder {
  private Map<MediaContentDesc.Field, String> _initialFields = new HashMap<>();
  private MediaContentDesc.Type _contentTypeToBuild;

  public MediaContentDescBuilder(@NotNull MediaContentDesc.Type contentTypeToBuild) {
    _contentTypeToBuild = contentTypeToBuild;
  }

  public IMediaContentDesc build() {
    return new MediaContentDesc(_initialFields, _contentTypeToBuild);
  }

  public MediaContentDescBuilder addField(@NotNull MediaContentDesc.Field name, @NotNull String value) {
    _initialFields.put(name, value);
    return this;
  }
}
