package com.mrlukashem.mediacontentprovider.data

import com.mrlukashem.mediacontentprovider.types.ContentType

class SharedPreferencesDataHandler : DataHandler {
    override fun search(wildCard: String): ContentViews {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(data: ContentViews): DataHandler.ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(updatedData: ContentViews): DataHandler.ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(data: ContentViews): DataHandler.ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(type: ContentType, selectionOptions: SelectionOptions?): DataHandler.ResultType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(queryView: QueryView): ContentViews {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}