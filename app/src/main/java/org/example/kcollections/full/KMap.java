package org.example.kcollections.full;

import java.util.*;
import java.util.function.*;

public final class KMap<K, V> implements Iterable<Map.Entry<K, V>> {

    private final Map<K, V> map;

    KMap(Map<K, V> map) {
        this.map = map;
    }

    public int size() { return map.size(); }

    public boolean isEmpty() { return map.isEmpty(); }

    public V get(K key) { return map.get(key); }

    public Set<K> keys() { return map.keySet(); }

    public Collection<V> values() { return map.values(); }

    public Set<Map.Entry<K, V>> entries() { return map.entrySet(); }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return map.entrySet().iterator();
    }

    /* ---------------- mapValues ---------------- */

    public <R> KMap<K, R> mapValues(
            Function<? super V, ? extends R> transform) {
        Objects.requireNonNull(transform);
        Map<K, R> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.put(entry.getKey(),
                    transform.apply(entry.getValue()));
        }
        return new KMap<>(result);
    }

    /* ---------------- mapKeys ---------------- */

    public <R> KMap<R, V> mapKeys(
            Function<? super K, ? extends R> transform) {
        Objects.requireNonNull(transform);
        Map<R, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.put(transform.apply(entry.getKey()),
                    entry.getValue());
        }
        return new KMap<>(result);
    }

    public Map<K, V> toMap() {
        return new LinkedHashMap<>(map);
    }
}
