package com.mrlukashem.mediacontentprovider;

import com.mrlukashem.mediacontentprovider.content.IMediaContentDesc;
import com.mrlukashem.mediacontentprovider.content.MediaContentDesc.*;

import com.mrlukashem.mediacontentprovider.content.MediaContentDescBuilder;
import org.junit.Assert;
import org.junit.Test;

public class MediaContentDescTests {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private IMediaContentDesc firstContentDesc = new MediaContentDescBuilder(Type.TRACK)
      .addField(Field.ALBUM, albumName)
      .addField(Field.ARTIST, artistName)
      .addField(Field.TITLE, titleName)
      .build();
  private IMediaContentDesc secondContentDesc = new MediaContentDescBuilder(Type.TRACK)
      .addField(Field.ALBUM, albumName)
      .addField(Field.ARTIST, artistName)
      .addField(Field.TITLE, titleName)
      .build();

  private IMediaContentDesc thirdContentDesc = new MediaContentDescBuilder(Type.ALBUM)
      .addField(Field.ALBUM, albumName)
      .addField(Field.ARTIST, artistName)
      .addField(Field.TITLE, titleName)
      .build();

  @Test
  public void getIDTest() {
    Assert.assertTrue(firstContentDesc.getKey() == secondContentDesc.getKey());
    Assert.assertTrue(firstContentDesc.getKey() != thirdContentDesc.getKey());
    Assert.assertTrue(secondContentDesc.getKey() != thirdContentDesc.getKey());
  }

  @Test
  public void getFieldTest() {
    IMediaContentDesc firstContentDesc = new MediaContentDescBuilder(Type.TRACK)
        .addField(Field.ALBUM, albumName)
        .addField(Field.ARTIST, artistName)
        .addField(Field.TITLE, titleName)
        .build();

    Assert.assertTrue(firstContentDesc.getField(Field.ALBUM).equals(albumName));
    Assert.assertTrue(firstContentDesc.getField(Field.ARTIST).equals(artistName));
    Assert.assertTrue(firstContentDesc.getField(Field.TITLE).equals(titleName));
    Assert.assertFalse(firstContentDesc.getField(Field.ARTIST).equals(albumName));
    Assert.assertFalse(firstContentDesc.getField(Field.ARTIST).equals(titleName));
  }
}
