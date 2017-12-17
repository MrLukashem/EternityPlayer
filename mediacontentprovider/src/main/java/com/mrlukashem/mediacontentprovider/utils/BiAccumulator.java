package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-29.
 */

public interface BiAccumulator<T> extends Accumulator<T, T, T> {
    @Override
    T invoke(T tObject1, T tObject2);
}
