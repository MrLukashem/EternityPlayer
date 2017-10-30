package com.mrlukashem.mediacontentprovider.providers

import android.content.Context
import com.mrlukashem.mediacontentprovider.content.IMediaContentDesc
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType

class MediaContentProvider(val appContext: Context?) : IMediaContentProvider {
  override fun getType(): ContentType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContent(contentType: ContentType, mediaDescType: MediaContentDescType,
                          maxCapacity: Int): List<IMediaContentDesc> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContent(contentType: ContentType, mediaDescType: MediaContentDescType,
                          resultCallback: (List<IMediaContentDesc>) -> Unit, maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentFromParent(contentParent: IMediaContentDesc, expectedResult: MediaContentDescType,
                                    maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentFromParent(contentParent: IMediaContentDesc, expectedResult: MediaContentDescType,
                                    resultCallback: (List<IMediaContentDesc>) -> Unit, maxCapacity: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getContentByKey(key: Int): IMediaContentDesc {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}