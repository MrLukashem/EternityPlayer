package com.mrlukashem.mediacontentprovider.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lmerta on 2017-11-15.
 */

public class ArrayStreamList<E> extends ForwardingList<E> implements StreamList<E> {
    public ArrayStreamList() {
        super(new ArrayList<>());
    }

    public ArrayStreamList(int capacity) {
        super(new ArrayList<>(capacity));
    }

    public ArrayStreamList(Collection<? extends E> source) {
        super(new ArrayList<>(source));
    }

    public Stream<E> getStream() {
        return new Stream<>(this);
    }
}
