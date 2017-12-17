package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-28.
 */

@FunctionalInterface
public interface Function<T, R> {
    R invoke(T tObject);
}
