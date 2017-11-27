package com.mrlukashem.mediacontentprovider.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lmerta on 2017-11-15.
 */

public class StreamArrayList<E> extends ArrayList<E> implements List<E> {
    public StreamArrayList() {
        super();
    }

    public StreamArrayList(int capacity) {
        super(capacity);
    }

    public StreamArrayList(Collection<? extends E> source) {
        super(source);
    }

    public Stream<E> getStream() {
        return new Stream<>(this);
    }

    @Override
    public List<E> streamForEach(Consumer<? super E> consumer) {
        return new Stream<>(this).mutableForEach(consumer).collect();
    }
}
