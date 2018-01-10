package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.generic.Builder
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

/*
 * Represents abstract Query - very similar to query from ContentResolver in Android.
 * @property contentType type of a content that a user want to fetch.
 * @property fieldsProjection, fields of a content that a user want to fetch.
 * @property setSelectionOption, a list of SelectionOption (filtering).
 * @property sortOption, sort type that a user want to apply for fetched content.
 */
data class QueryView(val contentType: ContentType,
                     val fieldsProjection: Set<MediaContentField.FieldName> = HashSet(),
                     val selectionOptions: Set<SelectionOption> = HashSet(),
                     val sortOption: SortOption? = null) {

  constructor(contentType: ContentType,
              selectionOptions: Set<SelectionOption> = HashSet(),
              sortOption: SortOption? = null,
              vararg fieldsProjection: MediaContentField.FieldName)
          : this(contentType, fieldsProjection.toSet(), selectionOptions, sortOption)

  /*
    The constructor is only for Java users of the class.
   */
  constructor(mainType: ContentType.MainType,
              subType: ContentType.SubType,
              vararg fieldsProjection: MediaContentField.FieldName)
          : this(ContentType(mainType, subType), fieldsProjection.toSet())

  constructor(mainType: ContentType.MainType,
              subType: ContentType.SubType,
              fieldsProjection: Set<MediaContentField.FieldName> = HashSet(),
              selectionOptions: Set<SelectionOption> = HashSet(),
              sortOption: SortOption? = null)
          : this(ContentType(mainType, subType), fieldsProjection, selectionOptions, sortOption)

  companion object QueryBuilder {
    fun build(init: QueryViewBuilder.() -> Unit) = QueryViewBuilder(init).build()
    fun from(viewBase: QueryView) = QueryViewBuilder().from(viewBase)
    fun create() = QueryViewBuilder()
  }

  class QueryViewBuilder() : Builder<QueryView> {
    var contentType: ContentType = ContentType()
    var sortOption: SortOption? = null
    var selectionOptions: MutableSet<SelectionOption> = HashSet()
    var fieldsProjection: MutableSet<MediaContentField.FieldName> = HashSet()

    constructor(init: QueryViewBuilder.() -> Unit): this() {
      init()
    }

    override fun build(): QueryView {
      return QueryView(contentType, fieldsProjection, selectionOptions, sortOption);
    }

    fun from(tBase: QueryView): QueryViewBuilder {
      contentType = tBase.contentType
      sortOption = tBase.sortOption
      selectionOptions = tBase.selectionOptions.toMutableSet()
      fieldsProjection = tBase.fieldsProjection.toMutableSet()

      return this
    }

    fun reset(): QueryViewBuilder {
      contentType = ContentType()
      sortOption = null
      selectionOptions.clear()
      fieldsProjection.clear()

      return this
    }

    /*
     * For Java users. Kotlin setter does not return this, it returns Unit (void).
     * @return this
     */
    fun setContentType(mainType: ContentType.MainType, subType: ContentType.SubType) = apply {
      contentType = ContentType(mainType, subType)
    }

    /*
     * For Java users. Kotlin setter does not return this, it returns Unit (void).
     * @return this
     */
    fun setSelectionOption(newOption: SelectionOption) = apply { selectionOptions.add(newOption) }

    /*
     * For Java users. Kotlin setter does not return this, it returns Unit (void).
     * @return this
     */
    fun setFieldProjection(newProjection: MediaContentField.FieldName) = apply {
      fieldsProjection.add(newProjection) }
  }

  /*
   * SortOption describe a sort type that a user want to apply to a MediaProvider result.
   */
  data class SortOption(val field: MediaContentField.FieldName, val sortType: SortType) {
    enum class SortType {
      DESC,
      ASC,
    }
  }

  /*
   * The class describes selection for data from a MediaProvider. It is used to filter rows by using
   * SelectionType sub enum class and a passed field in constructor.
   */
  data class SelectionOption(val field: MediaContentField, val type: SelectionType) {
    constructor(fieldName: MediaContentField.FieldName, fieldValue: String, type: SelectionType)
      : this(MediaContentField(fieldName, fieldValue), type)

    enum class SelectionType {
      EQUALS,
      EQUALS_GREATER,
      EQUALS_LESS,
      GREATER,
      LESS,
    }
  }
}