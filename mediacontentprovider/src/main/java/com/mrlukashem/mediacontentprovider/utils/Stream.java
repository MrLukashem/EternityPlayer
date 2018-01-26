package com.mrlukashem.mediacontentprovider.utils;

import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;

import org.jetbrains.annotations.NotNull;

/**
 * Created by lmerta on 2017-11-15.
 */

public class Stream<T> {
    private StreamList<T> tObjects;

    public Stream(@NotNull StreamList<T> tObjects) {
        this.tObjects = tObjects;
    }

    public Stream<T> filter(Predicate<? super T> predicate) {
        StreamList<T> tResObjects = new ArrayStreamList<>(tObjects.size());
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

    public Stream<T> forEach(@NotNull Consumer<? super T> consumer) {
        return forEachBase(consumer, tObjects);
    }

    public <R> Stream<R> map(@NotNull Function<T, R> mapper) {
        StreamList<R> rObjects = new ArrayStreamList<>();
        for (T tItem : tObjects) {
            rObjects.add(mapper.invoke(tItem));
        }

        return new Stream<>(rObjects);
    }

    public T reduce(@NonNull T initValue, @NonNull BiAccumulator<T> op) {
        return reduce(initValue, tObjects, op);
    }

    // TODO: Temporary it should does not work correctly because we should not modify object in tObject list.
    /*public Optional<T> reduce(@NonNull BiAccumulator<T> op) {
        // TODO: Temporary. We should not copy below list.
        StreamList<T> tempTObjects = new ArrayStreamList<>(tObjects.size());
        Collections.copy(tempTObjects, tObjects);
        Optional<T> tResult = tempTObjects.size() > 0 ?
                new Optional<>(tempTObjects.get(0)) : new Optional<>();

        if (tResult.isPresent()) {
            tResult.storedObject =
                    reduce(tResult.get(), tempTObjects.subStreamList(0, tempTObjects.size()), op);
        }

        return tResult;
    }*/

    public boolean allMatch(@NotNull Predicate<T> predicate) {
        return matchBase(t -> !predicate.apply(t), false);
    }

    public boolean anyMatch(@NotNull Predicate<T> predicate) {
        return matchBase(predicate, true);
    }

    @SuppressWarnings("unchecked")
    public StreamList<T> collect() {
        return new ArrayStreamList(tObjects);
    }

    private boolean matchBase(@NotNull Predicate<T> predicate, boolean returnValue) {
        for (T tItem : tObjects) {
            if (predicate.apply(tItem)) {
                return returnValue;
            }
        }

        return !returnValue;
    }

    private Stream<T> forEachBase(
            @NotNull Consumer<? super T> consumer, @NotNull StreamList<T> workStreamList) {
        for (T tItem : workStreamList) {
            consumer.accept(tItem);
        }

        return new Stream<>(workStreamList);
    }

    private T reduce(@NonNull T initValue, @NonNull StreamList<T> tStreamList, @NonNull BiAccumulator<T> op) {
        T tResult = initValue;
        for (T tItem: tStreamList) {
            tResult = op.invoke(tResult, tItem);
        }

        return tResult;
    }
}
