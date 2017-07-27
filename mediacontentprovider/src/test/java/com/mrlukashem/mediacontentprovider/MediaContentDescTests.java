package com.mrlukashem.mediacontentprovider;

import com.mrlukashem.mediacontentprovider.content.IMediaContentDesc;

import com.mrlukashem.mediacontentprovider.content.MediaContentDescBuilder;
import com.mrlukashem.mediacontentprovider.types.MediaContentDescField;
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType;
import org.junit.Assert;
import org.junit.Test;

public class MediaContentDescTests {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private IMediaContentDesc firstContentDesc = new MediaContentDescBuilder(MediaContentDescType.TRACK)
      .addField(MediaContentDescField.ALBUM, albumName)
      .addField(MediaContentDescField.ARTIST, artistName)
      .addField(MediaContentDescField.TITLE, titleName)
      .build();
  private IMediaContentDesc secondContentDesc = new MediaContentDescBuilder(MediaContentDescType.TRACK)
      .addField(MediaContentDescField.ALBUM, albumName)
      .addField(MediaContentDescField.ARTIST, artistName)
      .addField(MediaContentDescField.TITLE, titleName)
      .build();

  private IMediaContentDesc thirdContentDesc = new MediaContentDescBuilder(MediaContentDescType.ALBUM)
      .addField(MediaContentDescField.ALBUM, albumName)
      .addField(MediaContentDescField.ARTIST, artistName)
      .addField(MediaContentDescField.TITLE, titleName)
      .build();

  @Test
  public void getIDTest() {
    Assert.assertTrue(firstContentDesc.getKey() == secondContentDesc.getKey());
    Assert.assertTrue(firstContentDesc.getKey() != thirdContentDesc.getKey());
    Assert.assertTrue(secondContentDesc.getKey() != thirdContentDesc.getKey());
  }

  @Test
  public void getFieldTest() {
    IMediaContentDesc firstContentDesc = new MediaContentDescBuilder(MediaContentDescType.TRACK)
        .addField(MediaContentDescField.ALBUM, albumName)
        .addField(MediaContentDescField.ARTIST, artistName)
        .addField(MediaContentDescField.TITLE, titleName)
        .build();

    Assert.assertTrue(firstContentDesc.getField(MediaContentDescField.ALBUM).equals(albumName));
    Assert.assertTrue(firstContentDesc.getField(MediaContentDescField.ARTIST).equals(artistName));
    Assert.assertTrue(firstContentDesc.getField(MediaContentDescField.TITLE).equals(titleName));
    Assert.assertFalse(firstContentDesc.getField(MediaContentDescField.ARTIST).equals(albumName));
    Assert.assertFalse(firstContentDesc.getField(MediaContentDescField.ARTIST).equals(titleName));
  }
}
