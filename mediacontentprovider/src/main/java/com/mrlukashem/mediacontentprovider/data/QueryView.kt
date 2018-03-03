package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.generic.Buildable
import com.mrlukashem.mediacontentprovider.generic.Factory
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
                     val sortOptions: Set<FieldName> = HashSet(),
                     val sortType: SortType = SortType.DESC) {

    constructor(contentType: ContentType,
                selectionOptions: Set<String> = HashSet(),
                sortOptions: Set<FieldName> = HashSet(),
                vararg fieldsProjection: FieldName)
            : this(contentType, fieldsProjection.toSet(), selectionOptions, sortOptions)

    /*
        The constructor is only for Java users of the class.
    */
    constructor(contentType: ContentType, vararg fieldsProjection: FieldName)
            : this(contentType, fieldsProjection.toSet())

    constructor(contentType: ContentType, fieldsProjection: Set<FieldName>,
                vararg selectionOptions: String)
            : this(contentType, fieldsProjection, selectionOptions.toHashSet())

    companion object QueryBuilder : Factory<QueryViewBuilder> {
        fun build(init: QueryViewBuilder.() -> Unit) = QueryViewBuilder(init).build()
        fun from(viewBase: QueryView) = QueryViewBuilder().from(viewBase)
        override fun create() = QueryViewBuilder()
    }

    class QueryViewBuilder() : Buildable<QueryView, QueryViewBuilder> {
        var contentType: ContentType = ContentType.TRACK
        var sortType: SortType = QueryView.SortType.DESC
        var sortOptions: MutableSet<FieldName> = HashSet()
        var selectionOptions: MutableSet<SelectionOption> = HashSet()
        var rawSelectionOptions: MutableSet<String> = HashSet()
        var fieldsProjection: MutableSet<FieldName> = HashSet()

        constructor(init: QueryViewBuilder.() -> Unit): this() {
            init()
        }

        override fun build(): QueryView {
            rawSelectionOptions.addAll(convertToRawOptions(selectionOptions))
            return QueryView(contentType, fieldsProjection, rawSelectionOptions, sortOptions,
                    sortType)
        }

        override fun from(tBase: QueryView): QueryViewBuilder {
            contentType = tBase.contentType
            sortOptions = tBase.sortOptions.toMutableSet()
            rawSelectionOptions = tBase.selectionOptions.toMutableSet()
            fieldsProjection = tBase.fieldsProjection.toMutableSet()
            sortType = tBase.sortType

            return this
        }

        override fun reset(): QueryViewBuilder {
            contentType = ContentType.TRACK
            sortOptions.clear()
            selectionOptions.clear()
            rawSelectionOptions.clear()
            fieldsProjection.clear()

            return this
        }

        fun makeOptions(vararg options: String) = apply { rawSelectionOptions.addAll(options) }

        private fun convertToRawOptions(selectionOptions: MutableSet<SelectionOption>)
                : MutableSet<String> {
            val rawOptions: MutableSet<String> = HashSet()
            selectionOptions.mapTo(rawOptions) {
                val (field, value) = it.field
                StringBuilder(5).apply {
                    append(field?.name)
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
        fun setSortType(type: SortType) = apply {
            sortType = type
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
    enum class SortType {
        DESC,
        ASC,
    }

    enum class SelectionType {
        E,  // Equals
        EG, // Equals greater
        EL, // Equals less
        G,  // Greater
        L,  // Less
    }

    /*
    * The class describes selection for data from a MediaProvider. It is used to filter rows by using
    * SelectionType sub enum class and a passed field in constructor.
    */
    data class SelectionOption(val field: ContentField, val type: SelectionType) {
        constructor(fieldName: FieldName, fieldValue: String, type: SelectionType)
                : this(ContentField(fieldName, fieldValue), type)

        companion object Factory {
            fun create(fieldName: FieldName, fieldValue: String, type: SelectionType) =
                    SelectionOption(fieldName, fieldValue, type)
        }
    }
}
