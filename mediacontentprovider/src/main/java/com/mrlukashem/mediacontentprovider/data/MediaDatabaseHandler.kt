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

typealias HandleGenerator = (Cursor) -> String

class MediaDatabaseHandler(private val resolver: ContentResolver) : DataHandler {
    override fun query(queryView: QueryView): ContentViews {
        val extractor = QueryExtractor(queryView)
        return if (extractor.tableUri != null) doQuery(extractor) else emptyList()
    }

    private fun doQuery(extractor: QueryExtractor): ContentViews {
        extractor.extendsProjectionWithHandle()
        val generator = chooseHandleGenerateStrategy(extractor.tableUri)
        val cursor: Cursor? = resolver.query(
                extractor.tableUri,
                extractor.vanillaProjection,
                extractor.vanillaSelection,
                extractor.vanillaSelectionArgs,
                extractor.vanillaSortOrder)

        return buildContentViews(extractor.type, cursor, generator)
    }

    private fun QueryExtractor.extendsProjectionWithHandle() {
        val vanillaHandleField = convertUriToHandleFieldName(tableUri)
        if (vanillaHandleField.isNotEmpty()) {
            vanillaProjection = vanillaProjection.toMutableSet().apply {
                add(vanillaHandleField)
            }.toTypedArray()
        }
    }

    private fun fetchHandleBase(handleFields: Array<String>, cursor: Cursor): String? {
        var handleBase = ""
        var fieldsCaught = 0
        if (!cursor.isClosed) {
            handleFields.forEach {
                val idx = cursor.getColumnIndex(it)
                if (idx != -1) {
                    handleBase += cursor.getString(idx)
                    fieldsCaught++
                }
            }
        }

        return if (fieldsCaught == handleFields.size) handleBase else null
    }

    private fun convertUriToHandleFieldName(uri: Uri?) = when (uri) {
        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Albums.ALBUM
        MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Artists.ARTIST
        MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Playlists.DATA
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI -> MediaStore.Audio.Media.DATA
        else -> ""
    }

    private fun buildContentViews(type: ContentType, cursor: Cursor?,
                                  handleGenerator: HandleGenerator)
            : ContentViews {
        val result: MutableList<ContentView> = ArrayList()
        cursor?.use {
            while (cursor.moveToNext()) {
                val row: MutableSet<ContentField> = HashSet()
                (0 until cursor.columnCount).asSequence()
                        .mapNotNullTo(row) {
                            val field = Converter.fromVanillaField[cursor.getColumnName(it)]
                            if (field != null) ContentField(field,
                                    cursor.getString(it)) else null
                        }
                result.add(MediaContentView.build {
                    metaData[Constants.HANDLE_KEY_NAME] = handleGenerator(cursor)
                    contentType = type
                    contentFields = row
                })
            }
        }

        return result
    }

    override fun search(wildCardWorldWithType: WildCardUriPair): ContentViews {
        val (type, wildCard) = wildCardWorldWithType
        val uri = Converter.toUri[type]
        if (wildCard.isBlank() && uri != null) {
            return emptyList()
        }

        val fancySearchUri = Uri.withAppendedPath(uri, createFancySearchSegment(wildCard))
        val generator = chooseHandleGenerateStrategy(uri)
        return doSearch(type, fancySearchUri, generator)
    }

    private fun createFancySearchSegment(wildCard: String) = buildString {
        append(Constants.SEARCH_FANCY)
        append(wildCard)
    }

    private fun doSearch(type: ContentType, searchUri: Uri, generator: HandleGenerator):
            ContentViews {
        val cursor = resolver.query(
                searchUri, null, null, null, null)
        return buildContentViews(type, cursor, generator)
    }

    private fun chooseHandleGenerateStrategy(uri: Uri?): HandleGenerator {
        val baseField = convertUriToHandleFieldName(uri)
        when (baseField) {
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Artists.ARTIST -> {
                return { cursor ->
                    MediaStore.Audio.keyFor(fetchHandleBase(arrayOf(baseField), cursor))
                }
            }
            MediaStore.Audio.Playlists.DATA -> {
                return { cursor ->
                    fetchHandleBase(arrayOf(baseField), cursor)?.hashCode()?.toString()
                        ?: Constants.EMPTY_HANDLE_KEY
                }
            }
            else -> {
                return { _ ->  Constants.EMPTY_HANDLE_KEY }
            }
        }
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

    override fun delete(type: ContentType, selectionOptions: SelectionOptions?): ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private object Constants {
        const val EMPTY_HANDLE_KEY = ""
        const val HANDLE_KEY_NAME = "handleKey"
        const val SEARCH_FANCY = "search/fancy/"
    }

    private object Converter {
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

        val toUri: Map<ContentType, Uri> = hashMapOf(
                ContentType.ALBUM to MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                ContentType.ARTIST to MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                ContentType.PLAYLIST to MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                ContentType.TRACK to MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        )

        internal val fromVanillaField = toVanillaField.entries.associateBy({it.value}, {it.key})
        internal val fromUri = toUri.entries.associateBy({it.value}, {it.key})
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



        init {
            initializeWith(queryView)
        }

        fun initializeWith(queryView: QueryView) {
            vanillaProjection = queryView.fieldsProjection.mapNotNull {
                Converter.toVanillaField[it]
            }.toTypedArray()
            type = queryView.contentType
            tableUri = Converter.toUri[queryView.contentType]
        }
    }
}