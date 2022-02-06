package com.sb.resnyxbot.common;

import java.util.concurrent.atomic.AtomicReference;

public final class Pair<K, V> {

    private final AtomicReference<K> first = new AtomicReference<>();
    private final AtomicReference<V> second = new AtomicReference<>();

    public Pair() {
        this(null, null);
    }

    public Pair(K k, V v) {
        first.set(k);
        second.set(v);
    }

    public void replace(K k, V v) {
        this.first.set(k);
        this.second.set(v);
    }

    public boolean isEmpty() {
        return first.get() == null || second.get() == null;
    }

    public K first() {
        return first.get();
    }

    public V second() {
        return second.get();
    }
}
