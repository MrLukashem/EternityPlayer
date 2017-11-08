package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.content.IMediaContentView
import com.mrlukashem.mediacontentprovider.types.ContentType

class MediaDatabaseHandler : DataHandler {
  private val map: Map<String, (String) -> Unit> = mapOf(
          ContentType.AUDIO_ALBUM.name to {
            s: String -> print(s)
          }
  )

  override fun insert(data: List<IMediaContentView>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun update(oldData: List<IMediaContentView>, newData: List<IMediaContentView>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete(data: List<IMediaContentView>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete(query: Query) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun query(query: Query): List<IMediaContentView> {
  }
}