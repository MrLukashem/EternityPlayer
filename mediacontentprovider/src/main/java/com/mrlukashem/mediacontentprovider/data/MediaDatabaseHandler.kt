package com.mrlukashem.mediacontentprovider.data

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

import com.mrlukashem.mediacontentprovider.content.ContentView
import com.mrlukashem.mediacontentprovider.content.MediaContentView
import com.mrlukashem.mediacontentprovider.data.SelectionConstants.Companion.EQUALS
import com.mrlukashem.mediacontentprovider.data.DataHandler.*
import com.mrlukashem.mediacontentprovider.data.SelectionConstants.Companion.EQUALS_GREATER
import com.mrlukashem.mediacontentprovider.data.SelectionConstants.Companion.EQUALS_LESS
import com.mrlukashem.mediacontentprovider.data.SelectionConstants.Companion.GREATER
import com.mrlukashem.mediacontentprovider.data.SelectionConstants.Companion.LESS
import com.mrlukashem.mediacontentprovider.types.ContentField
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.FieldName

import kotlin.collections.HashSet

fun String.Companion.empty() = ""

typealias HandleGenerator = (Cursor) -> String
// TODO: ErrorListener support?
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
        else -> String.empty()
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

    override fun search(wildCard: String): ContentViews {
        val uri = Uri.parse(Constants.SEARCH_FANCY_PATH)
        if (wildCard.isBlank() && uri != null) {
            return emptyList()
        }

        val fancySearchUri = Uri.withAppendedPath(uri, wildCard)
        val generator = chooseHandleGenerateStrategy(uri, true)
        return doSearch(fancySearchUri, generator)
    }

    private fun doSearch(searchUri: Uri, generator: HandleGenerator):
            ContentViews {
        val cursor = resolver.query(
                searchUri, null, null, null, null)
        return buildContentViews(ContentType.TRACK, cursor, generator)
    }

    private fun chooseHandleGenerateStrategy(uri: Uri?, isFancy: Boolean = false): HandleGenerator {
        val baseField = if (isFancy) String.empty() else convertUriToHandleFieldName(uri)
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
        const val SEARCH_FANCY_PATH = "content://media/external/audio/search/fancy/"
        const val AND_OPERATOR = " AND "
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

        internal val toUri: Map<ContentType, Uri> = hashMapOf(
                ContentType.ALBUM to MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                ContentType.ARTIST to MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                ContentType.PLAYLIST to MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                ContentType.TRACK to MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        )

        internal val fromVanillaField = toVanillaField.entries.associateBy({it.value}, {it.key})
        internal val fromUri = toUri.entries.associateBy({it.value}, {it.key})

        private val selectionRegex =
                "(.+):($EQUALS|$EQUALS_GREATER|$EQUALS_LESS|$LESS|$GREATER):(.+)".toRegex()

        internal fun toVanillaSelection(selections: List<String>)
                : Pair<String?, Array<String>?> {
            val vanillaSelection = mutableListOf<String>()
            val vanillaSelectionArgs = mutableListOf<String>()
            selections.forEach {
                val matcher: MatchResult? = selectionRegex.matchEntire(it)
                if (matcher.isMatcherValid()) {
                    val fieldName = matcher!!.groupValues[1]
                    val op = matcher.groupValues[2]
                    val fieldValue = matcher.groupValues[3]

                    vanillaSelection.add("$fieldName ${toVanillaOperator(op)} ?")
                    vanillaSelectionArgs.add(fieldValue)
                }
            }

            return createFinalResult(vanillaSelection, vanillaSelectionArgs)
        }

        private fun MatchResult?.isMatcherValid() = this != null && groupValues.size == 4

        private fun toVanillaOperator(newStyleOperator: String) = when (newStyleOperator) {
            EQUALS -> "="
            EQUALS_GREATER -> ">="
            EQUALS_LESS -> "<="
            LESS -> "<"
            GREATER -> ">"
            else -> ""
        }

        private fun createFinalResult(vanillaSelection: MutableList<String>,
                                      vanillaSelectionArgs: MutableList<String>)
                : Pair<String?, Array<String>?> {
            return if (vanillaSelectionArgs.isNotEmpty() && vanillaSelectionArgs.isNotEmpty()) {
                Pair(joinSelectionElements(vanillaSelection), vanillaSelectionArgs.toTypedArray())
            } else {
                Pair(null, null)
            }
        }

        private fun joinSelectionElements(vanillaSelection: MutableList<String>): String {
            val resultBuilder = StringBuilder()
            vanillaSelection.forEachIndexed { idx, it ->
                resultBuilder.append(it)
                resultBuilder.takeIf {
                    vanillaSelection.size - 1 != idx }?.append(Constants.AND_OPERATOR)
            }

            return resultBuilder.toString()
        }
    }

    private class QueryExtractor(queryView: QueryView) {
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

            val (selection, selectionArgs) = Converter.toVanillaSelection(
                    queryView.selectionOptions.toMutableList())
            vanillaSelection = selection
            vanillaSelectionArgs = selectionArgs

            type = queryView.contentType
            tableUri = Converter.toUri[queryView.contentType]
        }

    }
}