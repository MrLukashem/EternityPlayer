package com.mrlukashem.mediacontentprovider.utils;

/**
 * Created by lmerta on 2017-11-15.
 */

public class Optional<T> {
    private T storedObject = null;

    public Optional(T storedObject) {
        this.storedObject = storedObject;
    }

    public Optional() {}

    public boolean isPresent() {
        return storedObject != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isPresent()) {
            consumer.accept(storedObject);
        }
    }

    public T get() {
        return storedObject;
    }
}
