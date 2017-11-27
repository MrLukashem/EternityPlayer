package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-15.
 */

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
