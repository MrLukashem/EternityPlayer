package com.mrlukashem.mediacontentprovider.utils;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by MrLukashem on 13.01.2018.
 */

abstract class ForwardingList<T> implements List<T> {
    private List<T> base;

    ForwardingList(@NonNull List<T> base) {
        this.base = base;
    }

    @Override
    public int size() {
        return base.size();
    }

    @Override
    public boolean isEmpty() {
        return base.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return base.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return base.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return base.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] t1s) {
        return base.toArray(t1s);
    }

    @Override
    public boolean add(T t) {
        return base.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return base.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return base.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        return base.addAll(collection);
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends T> collection) {
        return base.addAll(i, collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return base.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return base.retainAll(collection);
    }

    @Override
    public void clear() {
        base.clear();
    }

    @Override
    public T get(int i) {
        return base.get(i);
    }

    @Override
    public T set(int i, T t) {
        return base.set(i, t);
    }

    @Override
    public void add(int i, T t) {
        base.add(i, t);
    }

    @Override
    public T remove(int i) {
        return base.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return base.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return base.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return base.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int i) {
        return base.listIterator(i);
    }

    @NonNull
    @Override
    public List<T> subList(int i, int i1) {
        return base.subList(i, i1);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof List && base.equals(o);
    }

    @Override
    public int hashCode() {
        return 37 * base.hashCode();
    }
}
