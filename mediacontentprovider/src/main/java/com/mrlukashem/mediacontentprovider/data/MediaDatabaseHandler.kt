package com.mrlukashem.mediacontentprovider.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore

import com.mrlukashem.mediacontentprovider.content.MediaContentView
import com.mrlukashem.mediacontentprovider.data.DataHandler.*
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaDatabaseHandler(val resolver: ContentResolver? = null) : DataHandler {
  private val sourceTableUriFactory: Map<String, Uri> = mapOf(
          ContentType(ContentType.MainType.AUDIO, ContentType.SubType.ALBUM).toString()
                  to MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
          ContentType(ContentType.MainType.AUDIO, ContentType.SubType.ARTIST).toString()
                  to MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
          ContentType(ContentType.MainType.AUDIO, ContentType.SubType.PLAYLIST).toString()
                  to MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
          ContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK).toString()
                  to MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
  )

  override fun insert(data: List<MediaContentView>): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Template
  }

  override fun insert(data: MediaContentView): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun update(updatedData: List<MediaContentView>): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun update(updatedData: MediaContentView): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete(data: MediaContentView): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete(data: List<MediaContentView>): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete(
          contentType: ContentType,
          selectionOptions: List<QueryView.SelectionOption>): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete(
          mainType: ContentType.MainType,
          subType: ContentType.SubType,
          selectionOptions: List<QueryView.SelectionOption>): ResultType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun query(queryView: QueryView): List<MediaContentView> {
    val sourceTableUri = sourceTableUriFactory[queryView.contentType.toString()]
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun search(wildCardWorlds: List<String>): List<MediaContentView> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}