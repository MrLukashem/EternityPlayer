package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.generic.Builder
import com.mrlukashem.mediacontentprovider.types.ContentType
import com.mrlukashem.mediacontentprovider.types.MediaContentField

data class QueryView(val contentType: ContentType,
                val fieldsProjection: List<MediaContentField.FieldName> = ArrayList(),
                val selectionOptions: List<SelectionOption> = ArrayList(),
                val sortOption: SortOption? = null) {

  constructor(contentType: ContentType,
              selectionOptions: List<SelectionOption> = ArrayList(),
              sortOption: SortOption? = null,
              vararg fieldsProjection: MediaContentField.FieldName)
          : this(contentType, fieldsProjection.toList(), selectionOptions, sortOption)

  /*
    The constructor is only for Java users of the class.
   */
  constructor(mainType: ContentType.MainType,
              subType: ContentType.SubType,
              vararg fieldsProjection: MediaContentField.FieldName)
          : this(ContentType(mainType, subType), fieldsProjection.toList())

  constructor(mainType: ContentType.MainType,
              subType: ContentType.SubType,
              fieldsProjection: List<MediaContentField.FieldName> = ArrayList(),
              selectionOptions: List<SelectionOption> = ArrayList(),
              sortOption: SortOption? = null)
          : this(ContentType(mainType, subType), fieldsProjection, selectionOptions, sortOption)

  companion object {
    fun build(init: QueryViewBuilder.() -> Unit) = QueryViewBuilder(init).build()
    fun from(viewBase: QueryView) = QueryViewBuilder().from(viewBase)
  }

  class QueryViewBuilder() : Builder<QueryView> {
    lateinit var contentType: ContentType
    var sortOption: SortOption? = null
    var selectionOptions: List<SelectionOption> = ArrayList()
    var fieldsProjection: List<MediaContentField.FieldName> = ArrayList()

    constructor(init: QueryViewBuilder.() -> Unit): this() {
      init()
    }

    override fun from(tBase: QueryView): Builder<QueryView> {
      contentType = tBase.contentType
      sortOption = tBase.sortOption
      selectionOptions = tBase.selectionOptions
      fieldsProjection = tBase.fieldsProjection

      return this
    }

    override fun build(): QueryView {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
  }

  class SortOption(val field: MediaContentField.FieldName, val sortType: SortType) {
    enum class SortType {
      DESC,
      ASC,
    }
  }

  class SelectionOption(val field: MediaContentField, val type: SelectionType) {
    enum class SelectionType {
      EQUALS,
      EQUALS_GREATER,
      EQUALS_LESS,
      GREATER,
      LESS,
    }
  }
}