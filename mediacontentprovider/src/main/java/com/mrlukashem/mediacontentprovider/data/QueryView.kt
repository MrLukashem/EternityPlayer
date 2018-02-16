package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.generic.Buildable
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.ContentField
import com.mrlukashem.mediacontentprovider.types.FieldName

/*
 * Represents abstract Query - very similar to query from ContentResolver in Android.
 * @property contentType type of a content that a user want to fetch.
 * @property fieldsProjection, fields of a content that a user want to fetch.
 * @property setSelectionOption, a list of SelectionOption (filtering).
 * @property sortOption, sort type that a user want to apply for fetched content.
 */
data class QueryView(val contentType: ContentType,
                     val fieldsProjection: Set<FieldName> = HashSet(),
                     val selectionOptions: Set<String> = HashSet(),
                     val sortOption: SortOption? = null) {

    constructor(contentType: ContentType,
                selectionOptions: Set<String> = HashSet(),
                sortOption: SortOption? = null,
                vararg fieldsProjection: FieldName)
            : this(contentType, fieldsProjection.toSet(), selectionOptions, sortOption)

    /*
        The constructor is only for Java users of the class.
    */
    constructor(contentType: ContentType, vararg fieldsProjection: FieldName)
            : this(contentType, fieldsProjection.toSet())

    constructor(contentType: ContentType, fieldsProjection: Set<FieldName>,
                vararg selectionOptions: String)
            : this(contentType, fieldsProjection, selectionOptions.toHashSet())

    companion object QueryBuilder {
        fun build(init: QueryViewBuilder.() -> Unit) = QueryViewBuilder(init).build()
        fun from(viewBase: QueryView) = QueryViewBuilder().from(viewBase)
        fun create() = QueryViewBuilder()
    }

    class QueryViewBuilder() : Buildable<QueryView, QueryViewBuilder> {
        var contentType: ContentType = ContentType.TRACK
        var sortOption: SortOption? = null
        var selectionOptions: MutableSet<SelectionOption> = HashSet()
        var rawSelectionOptions: MutableSet<String> = HashSet()
        var fieldsProjection: MutableSet<FieldName> = HashSet()

        fun addOptions(vararg options: String) = apply { rawSelectionOptions.addAll(options) }

        constructor(init: QueryViewBuilder.() -> Unit): this() {
            init()
        }

        override fun build(): QueryView {
            rawSelectionOptions.addAll(convertToRawOptions(selectionOptions))
            return QueryView(contentType, fieldsProjection, rawSelectionOptions, sortOption)
        }

        override fun from(tBase: QueryView): QueryViewBuilder {
            contentType = tBase.contentType
            sortOption = tBase.sortOption
            rawSelectionOptions = tBase.selectionOptions.toMutableSet()
            fieldsProjection = tBase.fieldsProjection.toMutableSet()

            return this
        }

        override fun reset(): QueryViewBuilder {
            contentType = ContentType.TRACK
            sortOption = null
            selectionOptions.clear()
            rawSelectionOptions.clear()
            fieldsProjection.clear()

            return this
        }

        private fun convertToRawOptions(selectionOptions: MutableSet<SelectionOption>)
                : MutableSet<String> {
            val rawOptions: MutableSet<String> = HashSet()
            selectionOptions.mapTo(rawOptions) {
                val (field, value) = it.field
                StringBuilder(5).apply {
                    append(field)
                    append(":")
                    append(value)
                    append(":")
                    append(it.type.name)
                }.toString()
            }

            return rawOptions
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setType(type: ContentType) = apply {
            contentType = type
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setSelectionOption(newOption: SelectionOption) = apply {
            selectionOptions.add(newOption)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setRawSelectionOption(newOption: String) = apply {
            rawSelectionOptions.add(newOption)
        }

        /*
        * For Java users. Kotlin setter does not return this, it returns Unit (void).
        * @return this
        */
        fun setFieldProjection(newProjection: FieldName) = apply {
            fieldsProjection.add(newProjection)
        }
    }

    /*
    * SortOption describe a sort type that a user want to apply to a MediaProvider result.
    */
    data class SortOption(val field: FieldName, val sortType: SortType) {
        enum class SortType {
            DESC,
            ASC,
        }
    }

    /*
    * The class describes selection for data from a MediaProvider. It is used to filter rows by using
    * SelectionType sub enum class and a passed field in constructor.
    */
    data class SelectionOption(val field: ContentField, val type: SelectionType) {
        constructor(fieldName: FieldName, fieldValue: String, type: SelectionType)
                : this(ContentField(fieldName, fieldValue), type)

        enum class SelectionType {
            E,  // Equals
            EG, // Equals greater
            EL, // Equals less
            G,  // Greater
            L,  // Less
        }
    }
}
