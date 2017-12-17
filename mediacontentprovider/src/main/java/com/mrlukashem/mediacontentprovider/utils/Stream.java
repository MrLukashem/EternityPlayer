package com.mrlukashem.mediacontentprovider.utils;

import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by lmerta on 2017-11-15.
 */

public class Stream<T> {
    private List<T> tObjects;

    public Stream(@NotNull List<T> tObjects) {
        this.tObjects = tObjects;
    }

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

    public Stream<T> forEach(@NotNull Consumer<? super T> consumer) {
        return forEachBase(consumer, tObjects);
    }

    public <R> Stream<R> map(@NotNull Function<T, R> mapper) {
        List<R> rObjects = new StreamArrayList<>();
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
        List<T> tempTObjects = new StreamArrayList<>(tObjects.size());
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
    public List<T> collect() {
        ArrayList<T> arrayListView = (ArrayList<T>)(tObjects);
        return new StreamArrayList<>((List<T>)arrayListView.clone());
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
            @NotNull Consumer<? super T> consumer, @NotNull List<T> workList) {
        for (T tItem : workList) {
            consumer.accept(tItem);
        }

        return new Stream<>(workList);
    }

    private T reduce(@NonNull T initValue, @NonNull List<T> tList, @NonNull BiAccumulator<T> op) {
        T tResult = initValue;
        for (T tItem: tList) {
            tResult = op.invoke(tResult, tItem);
        }

        return tResult;
    }
}
