package com.mrlukashem.mediacontentprovider.types

/*data class ContentType(val mainType: MainType = MainType.AUDIO,
                       val subType: SubType = SubType.TRACK) {
    enum class MainType {
        AUDIO, VIDEO, IMAGE
    }

    enum class SubType {
        TRACK, PLAYLIST, ALBUM, ARTIST, VIDEO, IMAGE
    }
}*/

enum class ContentType {
    TRACK,
    PLAYLIST,
    ALBUM,
    ARTIST,
    VIDEO,
    IMAGE
}
