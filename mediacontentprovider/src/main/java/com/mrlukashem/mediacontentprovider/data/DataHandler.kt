package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.content.IMediaContentDesc

interface DataHandler {
  fun query(query: Query): List<IMediaContentDesc>
  fun insert(data: List<IMediaContentDesc>)
  fun update(oldData: List<IMediaContentDesc>, newData: List<IMediaContentDesc>)
  fun delete(data: List<IMediaContentDesc>)
  fun delete(query: Query)
}