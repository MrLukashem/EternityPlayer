package com.mrlukashem.mediacontentprovider;

import com.mrlukashem.mediacontentprovider.content.MediaContentView;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.junit.Assert;
import org.junit.Test;

public class MediaContentViewTest {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private MediaContentView firstContentDesc = new MediaContentView.MediaContentViewBuilder()
          .setType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
          .setField(MediaContentField.FieldName.ALBUM, albumName)
          .setField(MediaContentField.FieldName.ARTIST, artistName)
          .setField(MediaContentField.FieldName.TITLE, titleName)
          .build();
  private MediaContentView secondContentDesc = new MediaContentView.MediaContentViewBuilder()
          .setType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
          .setField(MediaContentField.FieldName.ALBUM, albumName)
          .setField(MediaContentField.FieldName.ARTIST, artistName)
          .setField(MediaContentField.FieldName.TITLE, titleName)
          .build();

  private MediaContentView thirdContentDesc = new MediaContentView.MediaContentViewBuilder()
          .setType(ContentType.MainType.AUDIO, ContentType.SubType.ALBUM)
          .setField(MediaContentField.FieldName.ALBUM, albumName)
          .setField(MediaContentField.FieldName.ARTIST, artistName)
          .setField(MediaContentField.FieldName.TITLE, titleName)
          .build();

  @Test
  public void getFieldTest() {
    MediaContentView firstContentDesc = new MediaContentView.MediaContentViewBuilder()
            .setType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
            .setField(MediaContentField.FieldName.ALBUM, albumName)
            .setField(MediaContentField.FieldName.ARTIST, artistName)
            .setField(MediaContentField.FieldName.TITLE, titleName)
            .build();

    Assert.assertTrue(
            firstContentDesc.getFieldValue(MediaContentField.FieldName.ALBUM).equals(albumName));
    Assert.assertTrue(
            firstContentDesc.getFieldValue(MediaContentField.FieldName.ARTIST).equals(artistName));
    Assert.assertTrue(
            firstContentDesc.getFieldValue(MediaContentField.FieldName.TITLE).equals(titleName));
    Assert.assertFalse(
            firstContentDesc.getFieldValue(MediaContentField.FieldName.ARTIST).equals(albumName));
    Assert.assertFalse(
            firstContentDesc.getFieldValue(MediaContentField.FieldName.ARTIST).equals(titleName));
  }
}
