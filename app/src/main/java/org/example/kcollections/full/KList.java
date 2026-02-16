package org.example.kcollections.full;

import java.util.*;
import java.util.function.*;

public final class KList<T> implements Iterable<T> {

    private final List<T> list;

    private KList(List<T> list) {
        this.list = list;
    }

    /* ============================================================
       Factory
       ============================================================ */

    public static <T> KList<T> empty() {
        return new KList<>(new ArrayList<>());
    }

    @SafeVarargs
    public static <T> KList<T> of(T... elements) {
        List<T> result = new ArrayList<>(elements.length);
        Collections.addAll(result, elements);
        return new KList<>(result);
    }

    public static <T> KList<T> from(Collection<T> collection) {
        return new KList<>(new ArrayList<>(collection));
    }

    public List<T> toList() {
        return new ArrayList<>(list);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public int size() { return list.size(); }

    public boolean isEmpty() { return list.isEmpty(); }

    public boolean isNotEmpty() { return !list.isEmpty(); }

    /* ============================================================
       Element Access (Strict Kotlin Semantics)
       ============================================================ */

    public T get(int index) {
        return list.get(index); // same exception behavior
    }

    public T getOrNull(int index) {
        return (index >= 0 && index < list.size()) ? list.get(index) : null;
    }

    public T elementAt(int index) {
        return get(index);
    }

    public T elementAtOrNull(int index) {
        return getOrNull(index);
    }

    public T first() {
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        return list.get(0);
    }

    public T first(Predicate<? super T> predicate) {
        for (T e : list)
            if (predicate.test(e)) return e;
        throw new NoSuchElementException("No element matching predicate was found.");
    }

    public T firstOrNull() {
        return list.isEmpty() ? null : list.get(0);
    }

    public T firstOrNull(Predicate<? super T> predicate) {
        for (T e : list)
            if (predicate.test(e)) return e;
        return null;
    }

    public T last() {
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        return list.get(list.size() - 1);
    }

    public T last(Predicate<? super T> predicate) {
        for (int i = list.size() - 1; i >= 0; i--) {
            T e = list.get(i);
            if (predicate.test(e)) return e;
        }
        throw new NoSuchElementException("No element matching predicate was found.");
    }

    public T lastOrNull() {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    public T lastOrNull(Predicate<? super T> predicate) {
        for (int i = list.size() - 1; i >= 0; i--) {
            T e = list.get(i);
            if (predicate.test(e)) return e;
        }
        return null;
    }

    public T single() {
        if (list.size() == 1) return list.get(0);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        throw new IllegalArgumentException("List has more than one element.");
    }

    public T singleOrNull() {
        return list.size() == 1 ? list.get(0) : null;
    }

    public T find(Predicate<? super T> predicate) {
        return firstOrNull(predicate);
    }

    public T findLast(Predicate<? super T> predicate) {
        return lastOrNull(predicate);
    }

    /* ============================================================
       Predicate Checks
       ============================================================ */

    public boolean any() {
        return !list.isEmpty();
    }

    public boolean any(Predicate<? super T> predicate) {
        for (T e : list)
            if (predicate.test(e)) return true;
        return false;
    }

    public boolean none() {
        return list.isEmpty();
    }

    public boolean none(Predicate<? super T> predicate) {
        for (T e : list)
            if (predicate.test(e)) return false;
        return true;
    }

    public boolean all(Predicate<? super T> predicate) {
        for (T e : list)
            if (!predicate.test(e)) return false;
        return true;
    }

    /* ============================================================
       Contains / Index
       ============================================================ */

    public boolean contains(Object element) {
        return list.contains(element);
    }

    public boolean containsAll(Collection<?> elements) {
        return list.containsAll(elements);
    }

    public int indexOf(Object element) {
        return list.indexOf(element);
    }

    public int lastIndexOf(Object element) {
        return list.lastIndexOf(element);
    }

    /* ============================================================
   Filtering Family
   ============================================================ */

    public KList<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (predicate.test(e)) result.add(e);
        }
        return new KList<>(result);
    }

    public KList<T> filterIndexed(BiPredicate<Integer, ? super T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            T e = list.get(i);
            if (predicate.test(i, e)) result.add(e);
        }
        return new KList<>(result);
    }

    public KList<T> filterNot(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (!predicate.test(e)) result.add(e);
        }
        return new KList<>(result);
    }

    @SuppressWarnings("unchecked")
    public <R> KList<R> filterNotNull() {
        List<R> result = new ArrayList<>();
        for (T e : list) {
            if (e != null) result.add((R) e);
        }
        return new KList<>(result);
    }

/* ============================================================
   Take / Drop Family
   ============================================================ */

    public KList<T> take(int n) {
        if (n <= 0) return empty();
        if (n >= list.size()) return this;
        return new KList<>(new ArrayList<>(list.subList(0, n)));
    }

    public KList<T> takeLast(int n) {
        if (n <= 0) return empty();
        int size = list.size();
        if (n >= size) return this;
        return new KList<>(new ArrayList<>(list.subList(size - n, size)));
    }

    public KList<T> takeWhile(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (!predicate.test(e)) break;
            result.add(e);
        }
        return new KList<>(result);
    }

    public KList<T> drop(int n) {
        if (n <= 0) return this;
        if (n >= list.size()) return empty();
        return new KList<>(new ArrayList<>(list.subList(n, list.size())));
    }

    public KList<T> dropLast(int n) {
        if (n <= 0) return this;
        int size = list.size();
        if (n >= size) return empty();
        return new KList<>(new ArrayList<>(list.subList(0, size - n)));
    }

    public KList<T> dropWhile(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        int index = 0;
        while (index < list.size() && predicate.test(list.get(index))) {
            index++;
        }
        return new KList<>(new ArrayList<>(list.subList(index, list.size())));
    }

/* ============================================================
   Mapping — Indexed Variants
   ============================================================ */

    public <R> KList<R> mapIndexed(BiFunction<Integer, ? super T, ? extends R> transform) {
        Objects.requireNonNull(transform);
        List<R> result = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            result.add(transform.apply(i, list.get(i)));
        }
        return new KList<>(result);
    }

    public <R> KList<R> mapIndexedNotNull(
            BiFunction<Integer, ? super T, ? extends R> transform) {
        Objects.requireNonNull(transform);
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            R r = transform.apply(i, list.get(i));
            if (r != null) result.add(r);
        }
        return new KList<>(result);
    }

    public <R> KList<R> flatMapIndexed(
            BiFunction<Integer, ? super T, ? extends Collection<? extends R>> transform) {
        Objects.requireNonNull(transform);
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Collection<? extends R> inner = transform.apply(i, list.get(i));
            if (inner != null) result.addAll(inner);
        }
        return new KList<>(result);
    }

    /* ============================================================
   Aggregation
   ============================================================ */

    public long count() {
        return list.size();
    }

    public long count(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        long c = 0;
        for (T e : list)
            if (predicate.test(e)) c++;
        return c;
    }

    /* -------------------- Reduce -------------------- */

    public T reduce(BinaryOperator<T> operation) {
        Objects.requireNonNull(operation);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        T acc = list.get(0);
        for (int i = 1; i < list.size(); i++)
            acc = operation.apply(acc, list.get(i));
        return acc;
    }

    public T reduceOrNull(BinaryOperator<T> operation) {
        Objects.requireNonNull(operation);
        if (list.isEmpty()) return null;
        T acc = list.get(0);
        for (int i = 1; i < list.size(); i++)
            acc = operation.apply(acc, list.get(i));
        return acc;
    }

    public T reduceIndexed(
            TriFunction<Integer, T, T, T> operation) {
        Objects.requireNonNull(operation);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        T acc = list.get(0);
        for (int i = 1; i < list.size(); i++)
            acc = operation.apply(i, acc, list.get(i));
        return acc;
    }

    public T reduceIndexedOrNull(
            TriFunction<Integer, T, T, T> operation) {
        Objects.requireNonNull(operation);
        if (list.isEmpty()) return null;
        T acc = list.get(0);
        for (int i = 1; i < list.size(); i++)
            acc = operation.apply(i, acc, list.get(i));
        return acc;
    }

    /* -------------------- Fold -------------------- */

    public <R> R fold(R initial, BiFunction<R, ? super T, R> operation) {
        Objects.requireNonNull(operation);
        R acc = initial;
        for (T e : list)
            acc = operation.apply(acc, e);
        return acc;
    }

    public <R> R foldIndexed(
            R initial,
            TriFunction<Integer, R, ? super T, R> operation) {
        Objects.requireNonNull(operation);
        R acc = initial;
        for (int i = 0; i < list.size(); i++)
            acc = operation.apply(i, acc, list.get(i));
        return acc;
    }

    /* -------------------- Running Variants -------------------- */

    public KList<T> runningReduce(BinaryOperator<T> operation) {
        Objects.requireNonNull(operation);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        List<T> result = new ArrayList<>(list.size());
        T acc = list.get(0);
        result.add(acc);
        for (int i = 1; i < list.size(); i++) {
            acc = operation.apply(acc, list.get(i));
            result.add(acc);
        }
        return new KList<>(result);
    }

    public <R> KList<R> runningFold(
            R initial,
            BiFunction<R, ? super T, R> operation) {
        Objects.requireNonNull(operation);
        List<R> result = new ArrayList<>(list.size() + 1);
        R acc = initial;
        result.add(acc);
        for (T e : list) {
            acc = operation.apply(acc, e);
            result.add(acc);
        }
        return new KList<>(result);
    }

/* ============================================================
   Numeric Aggregation
   ============================================================ */

    public int sumOfInt(ToIntFunction<? super T> selector) {
        Objects.requireNonNull(selector);
        int sum = 0;
        for (T e : list)
            sum += selector.applyAsInt(e);
        return sum;
    }

    public long sumOfLong(ToLongFunction<? super T> selector) {
        Objects.requireNonNull(selector);
        long sum = 0L;
        for (T e : list)
            sum += selector.applyAsLong(e);
        return sum;
    }

    public double sumOfDouble(ToDoubleFunction<? super T> selector) {
        Objects.requireNonNull(selector);
        double sum = 0.0;
        for (T e : list)
            sum += selector.applyAsDouble(e);
        return sum;
    }

    public double average(ToDoubleFunction<? super T> selector) {
        Objects.requireNonNull(selector);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        double sum = 0.0;
        for (T e : list)
            sum += selector.applyAsDouble(e);
        return sum / list.size();
    }

/* ============================================================
   Min / Max
   ============================================================ */

    @SuppressWarnings("unchecked")
    public T max() {
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        if (!(list.get(0) instanceof Comparable))
            throw new IllegalStateException("Elements must be Comparable.");
        T max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T e = list.get(i);
            if (((Comparable<T>) e).compareTo(max) > 0)
                max = e;
        }
        return max;
    }

    @SuppressWarnings("unchecked")
    public T min() {
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        if (!(list.get(0) instanceof Comparable))
            throw new IllegalStateException("Elements must be Comparable.");
        T min = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T e = list.get(i);
            if (((Comparable<T>) e).compareTo(min) < 0)
                min = e;
        }
        return min;
    }

    public T maxOrNull(Comparator<? super T> comparator) {
        if (list.isEmpty()) return null;
        T max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T e = list.get(i);
            if (comparator.compare(e, max) > 0)
                max = e;
        }
        return max;
    }

    public T minOrNull(Comparator<? super T> comparator) {
        if (list.isEmpty()) return null;
        T min = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T e = list.get(i);
            if (comparator.compare(e, min) < 0)
                min = e;
        }
        return min;
    }

    public <R extends Comparable<R>> T maxBy(Function<? super T, R> selector) {
        Objects.requireNonNull(selector);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        T best = list.get(0);
        R bestValue = selector.apply(best);
        for (int i = 1; i < list.size(); i++) {
            T e = list.get(i);
            R value = selector.apply(e);
            if (value.compareTo(bestValue) > 0) {
                best = e;
                bestValue = value;
            }
        }
        return best;
    }

    public <R extends Comparable<R>> T minBy(Function<? super T, R> selector) {
        Objects.requireNonNull(selector);
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        T best = list.get(0);
        R bestValue = selector.apply(best);
        for (int i = 1; i < list.size(); i++) {
            T e = list.get(i);
            R value = selector.apply(e);
            if (value.compareTo(bestValue) < 0) {
                best = e;
                bestValue = value;
            }
        }
        return best;
    }


    /* ============================================================
   Association
   ============================================================ */

    public <K, V> KMap<K, V> associate(
            Function<? super T, Pair<K, V>> transform) {
        Objects.requireNonNull(transform);
        Map<K, V> result = new LinkedHashMap<>();
        for (T e : list) {
            Pair<K, V> pair = transform.apply(e);
            result.put(pair.first(), pair.second()); // last wins
        }
        return new KMap<>(result);
    }

    public <K> KMap<K, T> associateBy(
            Function<? super T, ? extends K> keySelector) {
        Objects.requireNonNull(keySelector);
        Map<K, T> result = new LinkedHashMap<>();
        for (T e : list) {
            result.put(keySelector.apply(e), e);
        }
        return new KMap<>(result);
    }

    public <K, V> KMap<K, V> associateBy(
            Function<? super T, ? extends K> keySelector,
            Function<? super T, ? extends V> valueSelector) {
        Objects.requireNonNull(keySelector);
        Objects.requireNonNull(valueSelector);
        Map<K, V> result = new LinkedHashMap<>();
        for (T e : list) {
            result.put(keySelector.apply(e), valueSelector.apply(e));
        }
        return new KMap<>(result);
    }

    public <V> KMap<T, V> associateWith(
            Function<? super T, ? extends V> valueSelector) {
        Objects.requireNonNull(valueSelector);
        Map<T, V> result = new LinkedHashMap<>();
        for (T e : list) {
            result.put(e, valueSelector.apply(e));
        }
        return new KMap<>(result);
    }

/* ============================================================
   Grouping
   ============================================================ */

    public <K> KMap<K, KList<T>> groupBy(
            Function<? super T, ? extends K> keySelector) {
        Objects.requireNonNull(keySelector);
        Map<K, List<T>> map = new LinkedHashMap<>();
        for (T e : list) {
            K key = keySelector.apply(e);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(e);
        }
        return wrapGrouped(map);
    }

    public <K, V> KMap<K, KList<V>> groupBy(
            Function<? super T, ? extends K> keySelector,
            Function<? super T, ? extends V> valueSelector) {
        Objects.requireNonNull(keySelector);
        Objects.requireNonNull(valueSelector);
        Map<K, List<V>> map = new LinkedHashMap<>();
        for (T e : list) {
            K key = keySelector.apply(e);
            V value = valueSelector.apply(e);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        return wrapGrouped(map);
    }

    private static <K, V> KMap<K, KList<V>> wrapGrouped(
            Map<K, List<V>> map) {
        Map<K, KList<V>> result = new LinkedHashMap<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            result.put(entry.getKey(),
                    new KList<>(entry.getValue()));
        }
        return new KMap<>(result);
    }

/* ============================================================
   Zip
   ============================================================ */

    public <U> KList<Pair<T, U>> zip(Collection<U> other) {
        Objects.requireNonNull(other);
        Iterator<U> it = other.iterator();
        List<Pair<T, U>> result = new ArrayList<>();
        for (T e : list) {
            if (!it.hasNext()) break;
            result.add(Pair.of(e, it.next()));
        }
        return new KList<>(result);
    }

    public <U, R> KList<R> zip(
            Collection<U> other,
            BiFunction<? super T, ? super U, ? extends R> transform) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(transform);
        Iterator<U> it = other.iterator();
        List<R> result = new ArrayList<>();
        for (T e : list) {
            if (!it.hasNext()) break;
            result.add(transform.apply(e, it.next()));
        }
        return new KList<>(result);
    }

/* ============================================================
   zipWithNext
   ============================================================ */

    public KList<Pair<T, T>> zipWithNext() {
        List<Pair<T, T>> result = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            result.add(Pair.of(list.get(i), list.get(i + 1)));
        }
        return new KList<>(result);
    }

    public <R> KList<R> zipWithNext(
            BiFunction<? super T, ? super T, ? extends R> transform) {
        Objects.requireNonNull(transform);
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            result.add(transform.apply(list.get(i), list.get(i + 1)));
        }
        return new KList<>(result);
    }

/* ============================================================
   Chunked
   ============================================================ */

    public KList<KList<T>> chunked(int size) {
        return windowed(size, size, true);
    }

/* ============================================================
   Windowed
   ============================================================ */

    public KList<KList<T>> windowed(int size) {
        return windowed(size, 1, false);
    }

    public KList<KList<T>> windowed(int size, int step) {
        return windowed(size, step, false);
    }

    public KList<KList<T>> windowed(
            int size,
            int step,
            boolean partialWindows) {

        if (size <= 0)
            throw new IllegalArgumentException("size must be greater than zero.");
        if (step <= 0)
            throw new IllegalArgumentException("step must be greater than zero.");

        List<KList<T>> result = new ArrayList<>();

        int index = 0;
        while (index < list.size()) {
            int end = index + size;

            if (end > list.size() && !partialWindows) break;

            int actualEnd = Math.min(end, list.size());
            result.add(new KList<>(
                    new ArrayList<>(list.subList(index, actualEnd)))
            );

            index += step;
        }

        return new KList<>(result);
    }

/* ============================================================
   Windowed with Transform
   ============================================================ */

    public <R> KList<R> windowed(
            int size,
            int step,
            boolean partialWindows,
            Function<? super KList<T>, ? extends R> transform) {

        Objects.requireNonNull(transform);

        if (size <= 0)
            throw new IllegalArgumentException("size must be greater than zero.");
        if (step <= 0)
            throw new IllegalArgumentException("step must be greater than zero.");

        List<R> result = new ArrayList<>();

        int index = 0;
        while (index < list.size()) {
            int end = index + size;

            if (end > list.size() && !partialWindows) break;

            int actualEnd = Math.min(end, list.size());
            KList<T> window =
                    new KList<>(new ArrayList<>(list.subList(index, actualEnd)));

            result.add(transform.apply(window));

            index += step;
        }

        return new KList<>(result);
    }

/* ============================================================
   Ordering
   ============================================================ */

    @SuppressWarnings("unchecked")
    public KList<T> sorted() {
        if (list.isEmpty()) return this;
        if (!(list.get(0) instanceof Comparable))
            throw new IllegalStateException("Elements must be Comparable.");
        List<T> copy = new ArrayList<>(list);
        copy.sort((Comparator<? super T>) Comparator.naturalOrder());
        return new KList<>(copy);
    }

    @SuppressWarnings("unchecked")
    public KList<T> sortedDescending() {
        if (list.isEmpty()) return this;
        if (!(list.get(0) instanceof Comparable))
            throw new IllegalStateException("Elements must be Comparable.");
        List<T> copy = new ArrayList<>(list);
        copy.sort((Comparator<? super T>) Comparator.reverseOrder());
        return new KList<>(copy);
    }

    public <R extends Comparable<R>> KList<T> sortedBy(
            Function<? super T, ? extends R> selector) {
        Objects.requireNonNull(selector);
        List<T> copy = new ArrayList<>(list);
        copy.sort(Comparator.comparing(selector));
        return new KList<>(copy);
    }

    public <R extends Comparable<R>> KList<T> sortedByDescending(
            Function<? super T, ? extends R> selector) {
        Objects.requireNonNull(selector);
        List<T> copy = new ArrayList<>(list);
        copy.sort(Comparator.comparing(selector).reversed());
        return new KList<>(copy);
    }

/* ============================================================
   Distinct By
   ============================================================ */

    public <K> KList<T> distinctBy(
            Function<? super T, ? extends K> selector) {
        Objects.requireNonNull(selector);
        Set<K> seen = new HashSet<>();
        List<T> result = new ArrayList<>();
        for (T e : list) {
            K key = selector.apply(e);
            if (seen.add(key)) {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

/* ============================================================
   Set Algebra (Kotlin semantics)
   ============================================================ */

    public KList<T> union(Collection<? extends T> other) {
        Objects.requireNonNull(other);
        LinkedHashSet<T> set = new LinkedHashSet<>(list);
        set.addAll(other);
        return new KList<>(new ArrayList<>(set));
    }

    public KList<T> intersect(Collection<? extends T> other) {
        Objects.requireNonNull(other);
        Set<T> otherSet = new HashSet<>(other);
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (otherSet.contains(e)) {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

    public KList<T> subtract(Collection<? extends T> other) {
        Objects.requireNonNull(other);
        Set<T> otherSet = new HashSet<>(other);
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (!otherSet.contains(e)) {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

/* ============================================================
   Plus / Minus (Kotlin operators)
   ============================================================ */

    public KList<T> plus(T element) {
        List<T> result = new ArrayList<>(list.size() + 1);
        result.addAll(list);
        result.add(element);
        return new KList<>(result);
    }

    public KList<T> plus(Collection<? extends T> elements) {
        Objects.requireNonNull(elements);
        List<T> result = new ArrayList<>(list.size() + elements.size());
        result.addAll(list);
        result.addAll(elements);
        return new KList<>(result);
    }

    public KList<T> minus(T element) {
        List<T> result = new ArrayList<>(list.size());
        boolean removed = false;
        for (T e : list) {
            if (!removed && Objects.equals(e, element)) {
                removed = true; // remove first occurrence only
            } else {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

    public KList<T> minus(Collection<? extends T> elements) {
        Objects.requireNonNull(elements);
        Set<T> toRemove = new HashSet<>(elements);
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (!toRemove.contains(e)) {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

}

