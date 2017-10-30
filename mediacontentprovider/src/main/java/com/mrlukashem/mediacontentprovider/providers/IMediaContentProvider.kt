package com.mrlukashem.mediacontentprovider.providers

import com.mrlukashem.mediacontentprovider.content.IMediaContentDesc
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentDescType

interface IMediaContentProvider {
  fun getContentByKey(key: Int): IMediaContentDesc
  fun getType(): ContentType
  fun getContent(contentType: ContentType, mediaDescType: MediaContentDescType,
                 maxCapacity: Int = -1): List<IMediaContentDesc>
  fun getContent(contentType: ContentType, mediaDescType: MediaContentDescType,
                 resultCallback: (List<IMediaContentDesc>) -> Unit, maxCapacity: Int = -1)
  fun getContentFromParent(contentParent: IMediaContentDesc, expectedResult: MediaContentDescType,
                           maxCapacity: Int = -1)
  fun getContentFromParent(contentParent: IMediaContentDesc, expectedResult: MediaContentDescType,
                               resultCallback: (List<IMediaContentDesc>) -> Unit, maxCapacity: Int = -1)
}