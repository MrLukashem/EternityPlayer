package com.mrlukashem.mediacontentprovider.utils;

import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;

/**
 * Created by lmerta on 2017-11-15.
 */

public class Stream<T> {
    private List<T> tObjects;

    private Stream<T> forEachBase(
            @NonNull Consumer<? super T> consumer, @NonNull List<T> workList) {
        for (T tItem : workList) {
            consumer.accept(tItem);
        }

        return new Stream<>(workList);
    }

    public Stream(@NonNull List<T> tObjects) {
        this.tObjects = tObjects;
    }

    // TODO: Mutable filter.
    public Stream<T> filter(Predicate<? super T> predicate) {
        List<T> tResObjects = new StreamArrayList<>(tObjects.size());
        for (T tItem : tObjects) {
            if (predicate.apply(tItem)) {
                tResObjects.add(tItem);
            }
        }

        return new Stream<>(tResObjects);
    }

    public Optional<T> findFirst() {
        return tObjects.size() > 0 ?
                new Optional<>(tObjects.get(0)) : new Optional<>();
    }

    public Stream<T> mutableForEach(@NonNull Consumer<? super T> consumer) {
        return forEachBase(consumer, new StreamArrayList<>(tObjects));
    }

    public Stream<T> forEach(@NonNull Consumer<? super T> consumer) {
        return forEachBase(consumer, tObjects);
    }

    public List<T> collect() {
        return tObjects;
    }
}
