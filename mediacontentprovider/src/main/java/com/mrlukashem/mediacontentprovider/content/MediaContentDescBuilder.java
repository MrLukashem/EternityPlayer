package com.mrlukashem.mediacontentprovider.content;

import com.mrlukashem.mediacontentprovider.types.MediaContentDescField;
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MediaContentDescBuilder {
  private Map<MediaContentDescField, String> initialFields = new HashMap<>();
  private MediaContentDescType contentTypeToBuild;

  public MediaContentDescBuilder(@NotNull MediaContentDescType contentTypeToBuild) {
    this.contentTypeToBuild = contentTypeToBuild;
  }

  public IMediaContentDesc build() {
    return new MediaContentDesc(initialFields, contentTypeToBuild);
  }

  public MediaContentDescBuilder addField(@NotNull MediaContentDescField name, @NotNull String value) {
    initialFields.put(name, value);
    return this;
  }
}
