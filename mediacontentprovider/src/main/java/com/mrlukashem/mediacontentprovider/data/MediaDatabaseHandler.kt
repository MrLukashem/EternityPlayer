package com.mrlukashem.mediacontentprovider.data

import android.content.ContentResolver
import com.mrlukashem.mediacontentprovider.content.IMediaContentView

class MediaDatabaseHandler(val resolver: ContentResolver? = null) : DataHandler {
  private val map: Map<String, (String) -> Unit> = mapOf(

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
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}