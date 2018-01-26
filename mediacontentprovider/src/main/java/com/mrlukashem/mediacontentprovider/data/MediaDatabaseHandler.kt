package com.mrlukashem.mediacontentprovider.data

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.content.MediaContentView
import com.mrlukashem.mediacontentprovider.data.DataHandler.*
import com.mrlukashem.mediacontentprovider.types.ContentField
import com.mrlukashem.mediacontentprovider.types.ContentField.*
import com.mrlukashem.mediacontentprovider.types.ContentType

import kotlin.collections.HashSet

class MediaDatabaseHandler(private val resolver: ContentResolver) : DataHandler {
    private val fancySearchProjection = listOf(FieldName.MIME_TYPE, FieldName.ARTIST,
            FieldName.ALBUM, FieldName.ALBUM_ID, FieldName.TITLE, FieldName.DURATION, FieldName.ID)

    override fun query(queryView: QueryView): ContentViews {
        val extractor = QueryExtractor(queryView)
        return if (extractor.tableUri != null) doQuery(extractor) else emptyList()
    }

    private fun doQuery(extractor: QueryExtractor): ContentViews {
        val vanillaHandleField = extractor.extendsProjectionWithHandleField()
        val cursor: Cursor? = resolver.query(
                extractor.tableUri,
                extractor.vanillaProjection,
                extractor.vanillaSelection,
                extractor.vanillaSelectionArgs,
                extractor.vanillaSortOrder)

        return buildContentViews(extractor, cursor, vanillaHandleField)
    }

    private fun QueryExtractor.extendsProjectionWithHandleField(): String {
        val vanillaHandleField = convertUriToHandleBase(tableUri)
        if (vanillaHandleField.isNotEmpty()) {
            vanillaProjection = vanillaProjection.toMutableSet().apply {
                add(vanillaHandleField)
            }.toTypedArray()
        }

        return vanillaHandleField
    }

    private fun convertUriToHandleBase(uri: Uri?) = when (uri) {
        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Albums.ALBUM_ID
        MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Artists.ARTIST_KEY
        MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Playlists.DATA
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Media.DATA
        else -> ""
    }

    private fun buildContentViews(extractor: QueryExtractor, cursor: Cursor?, handleField: String)
            : ContentViews {
        val result: MutableList<ContentView> = ArrayList()
        cursor?.use {
            while (cursor.moveToNext()) {
                val row: MutableSet<ContentField> = HashSet()
                (0 until cursor.columnCount).asSequence()
                        .mapNotNullTo(row) {
                            val field = FieldsConverter.fromVanillaField[cursor.getColumnName(it)]
                            if (field != null) ContentField(field,
                                    cursor.getString(it)) else null
                        }
                result.add(MediaContentView.build {
                    contentHandle = generateHandle(handleField, cursor)
                    contentType = extractor.type
                    contentFields = row
                })
            }
        }

        return result
    }

    private fun generateHandle(handleField: String, cursor: Cursor): Int {
        var handleBase = ""
        if (!cursor.isClosed) {
            val idx = cursor.getColumnIndex(handleField)
            if (idx != -1) {
                handleBase = cursor.getString(idx)
            }
        }

        return if (handleBase.isNotEmpty())
            handleBase.hashCode() else MediaContentView.HANDLE_NOT_INITIALIZED
    }

    override fun search(wildCardWorlds: List<String>): ContentViews {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //
    }

    override fun insert(data: ContentViews): ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Template
    }
    override fun update(updatedData: ContentViews): ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(data: ContentViews): ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(
            mainType: ContentType.MainType,
            subType: ContentType.SubType,
            selectionOptions: SelectionOptions?): ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private object FieldsConverter {
        internal val toVanillaField = hashMapOf(
                FieldName.DATA to MediaStore.Audio.Media.DATA,
                FieldName.ALBUM to MediaStore.Audio.Albums.ALBUM,
                FieldName.ALBUM_ART to MediaStore.Audio.Albums.ALBUM_ART,
                FieldName.ALBUM_ID to MediaStore.Audio.Albums.ALBUM_ID,
                FieldName.ALBUM_KEY to MediaStore.Audio.Albums.ALBUM_KEY,
                FieldName.NUMBER_OF_SONGS to MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                FieldName.ARTIST to MediaStore.Audio.Albums.ARTIST,
                FieldName.ARTIST_ID to MediaStore.Audio.Albums.ALBUM_ID,
                FieldName.ARTIST_KEY to MediaStore.Audio.Albums.ALBUM_KEY,
                FieldName.NUMBER_OF_ALBUMS to MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                FieldName.NUMBER_OF_TRACKS to MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
                FieldName.BOOKMARK to MediaStore.Audio.Media.BOOKMARK,
                FieldName.DURATION to MediaStore.Audio.Media.DURATION,
                FieldName.TRACK to MediaStore.Audio.Media.TRACK,
                FieldName.TITLE_KEY to MediaStore.Audio.Media.TITLE_KEY,
                FieldName.TITLE to MediaStore.Audio.Media.TITLE,
                FieldName.MIME_TYPE to MediaStore.Audio.Media.MIME_TYPE,
                FieldName.ID to MediaStore.MediaColumns._ID
        )

        internal val fromVanillaField = toVanillaField.entries.associateBy({it.value}, {it.key})
    }

    private inner class QueryExtractor(queryView: QueryView) {
        lateinit var vanillaProjection: Array<String>
            internal set
        lateinit var type: ContentType
            internal set
        var vanillaSelection: String? = null
            internal set
        var vanillaSelectionArgs: Array<String>? = null
            internal set
        var vanillaSortOrder: String? = null
            internal set
        var tableUri: Uri? = null
            private set

        val uriFactory: Map<String, Uri> = hashMapOf(
                ContentType(ContentType.MainType.AUDIO, ContentType.SubType.ALBUM).toString()
                        to MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                ContentType(ContentType.MainType.AUDIO, ContentType.SubType.ARTIST).toString()
                        to MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                ContentType(ContentType.MainType.AUDIO, ContentType.SubType.PLAYLIST).toString()
                        to MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                ContentType(ContentType.MainType.AUDIO, ContentType.SubType.TRACK).toString()
                        to MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        )

        init {
            initializeWith(queryView)
        }

        fun initializeWith(queryView: QueryView) {
            vanillaProjection = queryView.fieldsProjection.mapNotNull {
                FieldsConverter.toVanillaField[it]
            }.toTypedArray()
            type = queryView.contentType
            tableUri = uriFactory[queryView.contentType.toString()]
        }
    }
}