package com.mrlukashem.mediacontentprovider.types

/**
 * Created by lmerta on 2017-11-08.
 */
class SelectionCriterion(val field: MediaContentField, op: Operator? = Operator.EQUAL) {
    enum class Operator {
        EQUAL,
        GREATER,
        LESS,
        GREATER_EQUAL,
        LESS_EQUAL,
    }
}