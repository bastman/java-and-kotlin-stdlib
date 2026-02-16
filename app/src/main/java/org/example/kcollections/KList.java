package org.example.kcollections;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class KList<T> {

    private final List<T> list;

    private KList(List<T> list) {
        this.list = list;
    }

    public static <T> KList<T> of(Collection<T> collection) {
        return new KList<>(new ArrayList<>(collection));
    }

    public static <T> KList<T> of(T... elements) {
        return new KList<>(new ArrayList<>(Arrays.asList(elements)));
    }

    public List<T> toList() {
        return list;
    }

    // filter
    public KList<T> filter(Predicate<? super T> predicate) {
        return new KList<>(
                list.stream()
                        .filter(predicate)
                        .collect(Collectors.toList())
        );
    }

    // map
    public <R> KList<R> map(Function<? super T, ? extends R> mapper) {
        return new KList<>(
                list.stream()
                        .map(mapper)
                        .collect(Collectors.toList())
        );
    }

    // flatMap
    public <R> KList<R> flatMap(Function<? super T, ? extends Collection<R>> mapper) {
        return new KList<>(
                list.stream()
                        .flatMap(e -> mapper.apply(e).stream())
                        .collect(Collectors.toList())
        );
    }

    // groupBy
    public <K> KMap<K, List<T>> groupBy(Function<? super T, ? extends K> classifier) {
        return new KMap<>(
                list.stream()
                        .collect(Collectors.groupingBy(classifier))
        );
    }

    // forEach
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
