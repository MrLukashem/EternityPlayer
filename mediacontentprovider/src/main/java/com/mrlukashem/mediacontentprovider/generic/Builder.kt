package com.mrlukashem.mediacontentprovider.generic

/**
 * Created by MrLukashem on 01.01.2018.
 */
interface Builder<T> {
    fun build() : T
    fun from(tBase: T) : Builder<T>
}