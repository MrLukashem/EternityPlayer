package com.mrlukashem.mediacontentprovider;

import com.mrlukashem.mediacontentprovider.content.ContentView;
import com.mrlukashem.mediacontentprovider.content.MediaContentView;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.FieldName;

import org.junit.Assert;
import org.junit.Test;

public class MediaContentViewTest {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private ContentView firstContentDesc = new MediaContentView.MediaContentViewBuilder()
          .setType(ContentType.TRACK)
          .setField(FieldName.ALBUM, albumName)
          .setField(FieldName.ARTIST, artistName)
          .setField(FieldName.TITLE, titleName)
          .build();
  private ContentView secondContentDesc = new MediaContentView.MediaContentViewBuilder()
          .setType(ContentType.TRACK)
          .setField(FieldName.ALBUM, albumName)
          .setField(FieldName.ARTIST, artistName)
          .setField(FieldName.TITLE, titleName)
          .build();

  private ContentView thirdContentDesc = new MediaContentView.MediaContentViewBuilder()
          .setType(ContentType.ALBUM)
          .setField(FieldName.ALBUM, albumName)
          .setField(FieldName.ARTIST, artistName)
          .setField(FieldName.TITLE, titleName)
          .build();

  @Test
  public void getFieldTest() {
      ContentView firstContentDesc = new MediaContentView.MediaContentViewBuilder()
            .setType(ContentType.TRACK)
            .setField(FieldName.ALBUM, albumName)
            .setField(FieldName.ARTIST, artistName)
            .setField(FieldName.TITLE, titleName)
            .build();

    Assert.assertTrue(
            firstContentDesc.get(FieldName.ALBUM).equals(albumName));
    Assert.assertTrue(
            firstContentDesc.get(FieldName.ARTIST).equals(artistName));
    Assert.assertTrue(
            firstContentDesc.get(FieldName.TITLE).equals(titleName));
    Assert.assertFalse(
            firstContentDesc.get(FieldName.ARTIST).equals(albumName));
    Assert.assertFalse(
            firstContentDesc.get(FieldName.ARTIST).equals(titleName));
  }
}
