package org.example.kcollections;

import java.util.*;
import java.util.function.*;

public final class KList<T> implements Iterable<T> {

    private final List<T> list;

    private KList(List<T> list) {
        this.list = list;
    }

    public static <T> KList<T> of(Collection<T> collection) {
        return new KList<>(new ArrayList<>(collection));
    }

    @SafeVarargs
    public static <T> KList<T> of(T... elements) {
        List<T> result = new ArrayList<>(elements.length);
        Collections.addAll(result, elements);
        return new KList<>(result);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public List<T> toList() {
        return new ArrayList<>(list); // defensive copy
    }

    // filter
    public KList<T> filter(Predicate<? super T> predicate) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return new KList<>(result);
    }


    // map
    public <R> KList<R> map(Function<? super T, ? extends R> transform) {
        int size = list.size();
        List<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(transform.apply(list.get(i)));
        }
        return new KList<>(result);
    }

    // flatMap

    public <R> KList<R> flatMap(Function<? super T, ? extends Collection<R>> transform) {
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Collection<R> inner = transform.apply(list.get(i));
            result.addAll(inner);
        }
        return new KList<>(result);
    }

    // groupBy
    public <K> KMap<K, List<T>> groupBy(Function<? super T, ? extends K> keySelector) {
        Map<K, List<T>> result = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            K key = keySelector.apply(element);

            List<T> group = result.get(key);
            if (group == null) {
                group = new ArrayList<>();
                result.put(key, group);
            }

            group.add(element);
        }

        return new KMap<>(result);
    }

    public <K> KMap<K, T> associateBy(Function<? super T, ? extends K> keySelector) {
        Map<K, T> result = new HashMap<>(list.size());

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            K key = keySelector.apply(element);
            result.put(key, element);
        }

        return new KMap<>(result);
    }

    public KList<KList<T>> chunked(int size) {
        if (size <= 0) throw new IllegalArgumentException("size must be > 0");

        List<KList<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            chunks.add(new KList<>(
                    list.subList(i, Math.min(i + size, list.size()))
            ));
        }
        return new KList<>(chunks);
    }

    public KPair<KList<T>, KList<T>> partition(Predicate<? super T> predicate) {
        List<T> matching = new ArrayList<>();
        List<T> nonMatching = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            if (predicate.test(element)) {
                matching.add(element);
            } else {
                nonMatching.add(element);
            }
        }

        return KPair.of(new KList<>(matching), new KList<>(nonMatching));
    }

    public KList<T> distinct() {
        Set<T> seen = new LinkedHashSet<>();
        List<T> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            if (seen.add(element)) {
                result.add(element);
            }
        }

        return new KList<>(result);
    }

    public KList<T> sortedBy(Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>(list);
        result.sort(comparator);
        return new KList<>(result);
    }

    public T first() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return list.get(0);
    }

    public T firstOrNull() {
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean any(Predicate<? super T> predicate) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (predicate.test(list.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean none(Predicate<? super T> predicate) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public long count(Predicate<? super T> predicate) {
        long count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                count++;
            }
        }
        return count;
    }

    // forEach
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    public KList<T> onEach(Consumer<? super T> action) {
        list.forEach(action);
        return this;
    }

    public <R> R let(Function<KList<T>, R> transformer) {
        return transformer.apply(this);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
