package com.mrlukashem.mediacontentprovider.generic

/**
 * Created by MrLukashem on 16.02.2018.
 */
interface Factory<out T> {
    fun create(): T
}