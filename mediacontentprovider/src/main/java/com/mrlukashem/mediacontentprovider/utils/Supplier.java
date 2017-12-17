package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-28.
 */

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
