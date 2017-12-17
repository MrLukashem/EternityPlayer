package com.mrlukashem.mediacontentprovider.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by MrLukashem on 10.12.2017.
 */

public interface Predicate<T> {
    boolean test(@NotNull T tObject);
    default Predicate<T> negate() {
        return tObject -> !test(tObject);
    }
    default Predicate<T> or(Predicate<T> other) {
        return tObject -> test(tObject) || other.test(tObject);
    }
    default Predicate<T> and(Predicate<T> other) {
        return tObject -> test(tObject) && other.test(tObject);
    }
}
