package org.example.kcollections;

import java.util.*;
import java.util.function.*;

public final class KMap<K, V> {

    private final Map<K, V> map;

    KMap(Map<K, V> map) {
        this.map = map;
    }

    public Map<K, V> toMap() {
        return map;
    }

    public <R> KMap<K, R> mapValues(Function<? super V, ? extends R> mapper) {
        Map<K, R> result = new HashMap<>();
        for (var entry : map.entrySet()) {
            result.put(entry.getKey(), mapper.apply(entry.getValue()));
        }
        return new KMap<>(result);
    }
}
