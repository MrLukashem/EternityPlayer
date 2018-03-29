package com.mrlukashem.mediacontentprovider.utils

/**
 * Created by MrLukashem on 19.03.2018.
 */
inline fun <T, R> T?.ifNotNull(block: T.() -> R) = this?.block()
