package com.mrlukashem.mediacontentprovider;

import android.test.ActivityTestCase;

import com.mrlukashem.mediacontentprovider.content.IMediaContentView;

import com.mrlukashem.mediacontentprovider.content.MediaContentViewBuilder;
import com.mrlukashem.mediacontentprovider.types.ContentType;
import com.mrlukashem.mediacontentprovider.types.MediaContentField;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

public class MediaContentViewTests extends ActivityTestCase {
  private String albumName = "The Book of Souls";
  private String artistName = "Iron Maiden";
  private String titleName = "Speed of Light";

  private IMediaContentView firstContentDesc = new MediaContentViewBuilder(
          ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
          .addField(MediaContentField.FieldName.ALBUM, albumName)
          .addField(MediaContentField.FieldName.ARTIST, artistName)
          .addField(MediaContentField.FieldName.TITLE, titleName)
          .build();
  private IMediaContentView secondContentDesc = new MediaContentViewBuilder(
          ContentType.MainType.AUDIO, ContentType.SubType.TRACK)
          .addField(MediaContentField.FieldName.ALBUM, albumName)
          .addField(MediaContentField.FieldName.ARTIST, artistName)
          .addField(MediaContentField.FieldName.TITLE, titleName)
          .build();

  private IMediaContentView thirdContentDesc = new MediaContentViewBuilder(
          ContentType.MainType.AUDIO, ContentType.SubType.ALBUM)
          .addField(MediaContentField.FieldName.ALBUM, albumName)
          .addField(MediaContentField.FieldName.ARTIST, artistName)
          .addField(MediaContentField.FieldName.TITLE, titleName)
          .build();

  @Test
  public void getIDTest() {
    Assert.assertTrue(firstContentDesc.getContentKey() == secondContentDesc.getContentKey());
    Assert.assertTrue(firstContentDesc.getContentKey() != thirdContentDesc.getContentKey());
    Assert.assertTrue(secondContentDesc.getContentKey() != thirdContentDesc.getContentKey());
  }

  @Test
  public void getFieldTest() {
    IMediaContentView firstContentDesc = new MediaContentViewBuilder(
            ContentType.MainType.AUDIO,
            ContentType.SubType.TRACK)
            .addField(MediaContentField.FieldName.ALBUM, albumName)
            .addField(MediaContentField.FieldName.ARTIST, artistName)
            .addField(MediaContentField.FieldName.TITLE, titleName)
            .build();

    Assert.assertTrue(firstContentDesc.getFieldValue(MediaContentField.FieldName.ALBUM).equals(albumName));
    Assert.assertTrue(firstContentDesc.getFieldValue(MediaContentField.FieldName.ARTIST).equals(artistName));
    Assert.assertTrue(firstContentDesc.getFieldValue(MediaContentField.FieldName.TITLE).equals(titleName));
    Assert.assertFalse(firstContentDesc.getFieldValue(MediaContentField.FieldName.ARTIST).equals(albumName));
    Assert.assertFalse(firstContentDesc.getFieldValue(MediaContentField.FieldName.ARTIST).equals(titleName));
  }
}
