package com.mrlukashem.mediacontentprovider.types

data class ContentType(val mainType: MainType, val subType: SubType) {
    enum class MainType {
        AUDIO, VIDEO, IMAGE
    }

    enum class SubType {
        TRACK, PLAYLIST, ALBUM, ARTIST
    }
}
