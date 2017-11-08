package com.mrlukashem.mediacontentprovider;

import com.mrlukashem.mediacontentprovider.content.IMediaContentView;

import com.mrlukashem.mediacontentprovider.content.MediaContentViewBuilder;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.junit.Assert;
import org.junit.Test;

public class MediaContentViewTests {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private IMediaContentView firstContentDesc = new MediaContentViewBuilder(ContentType.AUDIO_TRACK)
      .addField(MediaContentField.ALBUM, albumName)
      .addField(MediaContentField.ARTIST, artistName)
      .addField(MediaContentField.TITLE, titleName)
      .build();
  private IMediaContentView secondContentDesc = new MediaContentViewBuilder(ContentType.AUDIO_TRACK)
      .addField(MediaContentField.ALBUM, albumName)
      .addField(MediaContentField.ARTIST, artistName)
      .addField(MediaContentField.TITLE, titleName)
      .build();

  private IMediaContentView thirdContentDesc = new MediaContentViewBuilder(ContentType.AUDIO_ALBUM)
      .addField(MediaContentField.ALBUM, albumName)
      .addField(MediaContentField.ARTIST, artistName)
      .addField(MediaContentField.TITLE, titleName)
      .build();

  @Test
  public void getIDTest() {
    Assert.assertTrue(firstContentDesc.getKey() == secondContentDesc.getKey());
    Assert.assertTrue(firstContentDesc.getKey() != thirdContentDesc.getKey());
    Assert.assertTrue(secondContentDesc.getKey() != thirdContentDesc.getKey());
  }

  @Test
  public void getFieldTest() {
    IMediaContentView firstContentDesc = new MediaContentViewBuilder(ContentType.AUDIO_TRACK)
        .addField(MediaContentField.ALBUM, albumName)
        .addField(MediaContentField.ARTIST, artistName)
        .addField(MediaContentField.TITLE, titleName)
        .build();

    Assert.assertTrue(firstContentDesc.getField(MediaContentField.ALBUM).equals(albumName));
    Assert.assertTrue(firstContentDesc.getField(MediaContentField.ARTIST).equals(artistName));
    Assert.assertTrue(firstContentDesc.getField(MediaContentField.TITLE).equals(titleName));
    Assert.assertFalse(firstContentDesc.getField(MediaContentField.ARTIST).equals(albumName));
    Assert.assertFalse(firstContentDesc.getField(MediaContentField.ARTIST).equals(titleName));
  }
}
