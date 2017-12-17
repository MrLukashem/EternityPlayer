package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-29.
 */

@FunctionalInterface
public interface Accumulator<T, E, R> {
    R invoke(T tObject, E eObject);
}
