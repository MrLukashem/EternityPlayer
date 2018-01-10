package com.mrlukashem.mediacontentprovider;

import com.mrlukashem.mediacontentprovider.content.IMediaContentView;

import com.mrlukashem.mediacontentprovider.content.MediaContentView;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.junit.Assert;
import org.junit.Test;

public class MediaContentViewTest {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private IMediaContentView firstContentDesc = new MediaContentView.MediaContentViewBuilder()
          .contentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
          .contentField(MediaContentField.FieldName.ALBUM, albumName)
          .contentField(MediaContentField.FieldName.ARTIST, artistName)
          .contentField(MediaContentField.FieldName.TITLE, titleName)
          .build();
  private IMediaContentView secondContentDesc = new MediaContentView.MediaContentViewBuilder()
          .contentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
          .contentField(MediaContentField.FieldName.ALBUM, albumName)
          .contentField(MediaContentField.FieldName.ARTIST, artistName)
          .contentField(MediaContentField.FieldName.TITLE, titleName)
          .build();

  private IMediaContentView thirdContentDesc = new MediaContentView.MediaContentViewBuilder()
          .contentType(ContentType.MainType.AUDIO, ContentType.SubType.ALBUM)
          .contentField(MediaContentField.FieldName.ALBUM, albumName)
          .contentField(MediaContentField.FieldName.ARTIST, artistName)
          .contentField(MediaContentField.FieldName.TITLE, titleName)
          .build();

  @Test
  public void getFieldTest() {
    IMediaContentView firstContentDesc = new MediaContentView.MediaContentViewBuilder()
            .contentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
            .contentField(MediaContentField.FieldName.ALBUM, albumName)
            .contentField(MediaContentField.FieldName.ARTIST, artistName)
            .contentField(MediaContentField.FieldName.TITLE, titleName)
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
