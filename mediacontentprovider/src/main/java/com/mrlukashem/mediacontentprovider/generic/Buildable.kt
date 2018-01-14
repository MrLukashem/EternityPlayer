package com.mrlukashem.mediacontentprovider.generic

/**
 * Created by MrLukashem on 01.01.2018.
 */
interface Buildable<T, out R> {
    fun build() : T
    fun from(tBase: T): R
    fun reset(): R
}