package com.mrlukashem.mediacontentprovider.providers

import com.mrlukashem.mediacontentprovider.content.IMediaContentDesc
import com.mrlukashem.mediacontentprovider.types.ContentProviderType
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType

interface IMediaContentProvider {
  fun getContentByKey(key: Int): IMediaContentDesc
  fun getType(): ContentProviderType
  fun getContent(maxCapacity: Int = -1): List<IMediaContentDesc>
  fun <T> getContent(resultCallback: (List<IMediaContentDesc>) -> T, maxCapacity: Int = -1)
  fun getContentFromParent(contentParent: IMediaContentDesc, expectedResult: MediaContentDescType,
                           maxCapacity: Int = -1)
  fun <T> getContentFromParent(contentParent: IMediaContentDesc, expectedResult: MediaContentDescType,
                              resultCallback: (List<IMediaContentDesc>) -> T, maxCapacity: Int = -1)
}