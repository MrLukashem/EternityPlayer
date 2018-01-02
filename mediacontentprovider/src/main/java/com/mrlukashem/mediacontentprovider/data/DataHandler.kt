package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.content.IMediaContentView

interface DataHandler {
  fun query(queryView: QueryView): List<IMediaContentView>
  fun search(wildCardWorlds: List<String>): List<IMediaContentView>
  fun insert(data: List<IMediaContentView>)
  fun update(oldData: List<IMediaContentView>, newData: List<IMediaContentView>)
  fun delete(data: List<IMediaContentView>)
  fun delete(queryView: QueryView)
}