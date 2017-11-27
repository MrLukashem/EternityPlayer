package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-15.
 */

public interface List<E> extends java.util.List<E> {
    Stream<E> getStream();
    List<E> streamForEach(Consumer<? super E> consumer);
}
