package org.example.kcollections.full.v2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.random.RandomGenerator;
import java.util.function.BiFunction;
import java.util.function.ToIntFunction;
import java.util.function.*;
public final class KList<T> implements List<T>, RandomAccess {

    private final List<T> backing;

    /* ============================================================
       Constructors / Factory
       ============================================================ */

    public KList(List<T> backing) {
        if (backing == null) {
            throw new NullPointerException("backing list is null");
        }
        this.backing = backing;
    }

    public static <T> KList<T> of(List<T> list) {
        return new KList<>(list);
    }

    /* ============================================================
       Basic List Delegation
       ============================================================ */

    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backing.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return backing.iterator();
    }

    @Override
    public Object[] toArray() {
        return backing.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return backing.toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backing.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public T get(int index) {
        return backing.get(index); // preserves IndexOutOfBoundsException
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Read-only Kotlin List");
    }

    @Override
    public int indexOf(Object o) {
        return backing.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backing.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return backing.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return backing.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return backing.subList(fromIndex, toIndex);
    }

    /* ============================================================
       Element Retrieval Family (Kotlin Parity)
       ============================================================ */

    public T getOrNull(int index) {
        return (index >= 0 && index < size()) ? backing.get(index) : null;
    }

    public T getOrElse(int index, Function<Integer, ? extends T> defaultValue) {
        if (index >= 0 && index < size()) {
            return backing.get(index);
        }
        return defaultValue.apply(index);
    }

    public T elementAt(int index) {
        return get(index); // same semantics
    }

    public T elementAtOrNull(int index) {
        return getOrNull(index);
    }

    public T elementAtOrElse(int index, Function<Integer, ? extends T> defaultValue) {
        return getOrElse(index, defaultValue);
    }

    /* ---------- first ---------- */

    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return backing.get(0);
    }

    public T first(Predicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                return e;
            }
        }
        throw new NoSuchElementException("No element matching predicate.");
    }

    public T firstOrNull() {
        return isEmpty() ? null : backing.get(0);
    }

    public T firstOrNull(Predicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                return e;
            }
        }
        return null;
    }

    /* ---------- last ---------- */

    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return backing.get(size() - 1);
    }

    public T last(Predicate<? super T> predicate) {
        for (int i = size() - 1; i >= 0; i--) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                return e;
            }
        }
        throw new NoSuchElementException("No element matching predicate.");
    }

    public T lastOrNull() {
        return isEmpty() ? null : backing.get(size() - 1);
    }

    public T lastOrNull(Predicate<? super T> predicate) {
        for (int i = size() - 1; i >= 0; i--) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                return e;
            }
        }
        return null;
    }

    /* ---------- single ---------- */

    public T single() {
        int size = size();
        if (size == 0) {
            throw new NoSuchElementException("List is empty.");
        }
        if (size > 1) {
            throw new IllegalArgumentException("List has more than one element.");
        }
        return backing.get(0);
    }

    public T single(Predicate<? super T> predicate) {
        T single = null;
        boolean found = false;

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                if (found) {
                    throw new IllegalArgumentException("More than one matching element.");
                }
                single = e;
                found = true;
            }
        }

        if (!found) {
            throw new NoSuchElementException("No element matching predicate.");
        }

        return single;
    }

    public T singleOrNull() {
        return size() == 1 ? backing.get(0) : null;
    }

    public T singleOrNull(Predicate<? super T> predicate) {
        T single = null;
        boolean found = false;

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                if (found) {
                    return null;
                }
                single = e;
                found = true;
            }
        }

        return found ? single : null;
    }

    /* ---------- random ---------- */

    public T random() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        int index = RandomGenerator.getDefault().nextInt(size());
        return backing.get(index);
    }

    public T random(RandomGenerator random) {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return backing.get(random.nextInt(size()));
    }



        /* ============================================================
       Predicate Family
       ============================================================ */

    /* ---------- all ---------- */

    public boolean all(Predicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            if (!predicate.test(backing.get(i))) {
                return false; // short-circuit
            }
        }
        return true; // true for empty list
    }

    public boolean allIndexed(IndexedPredicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            if (!predicate.test(i, backing.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* ---------- any ---------- */

    public boolean any() {
        return !isEmpty();
    }

    public boolean any(Predicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            if (predicate.test(backing.get(i))) {
                return true; // short-circuit
            }
        }
        return false;
    }

    public boolean anyIndexed(IndexedPredicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            if (predicate.test(i, backing.get(i))) {
                return true;
            }
        }
        return false;
    }

    /* ---------- none ---------- */

    public boolean none() {
        return isEmpty();
    }

    public boolean none(Predicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            if (predicate.test(backing.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean noneIndexed(IndexedPredicate<? super T> predicate) {
        for (int i = 0; i < size(); i++) {
            if (predicate.test(i, backing.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* ---------- count ---------- */

    public int count() {
        return size();
    }

    public int count(Predicate<? super T> predicate) {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (predicate.test(backing.get(i))) {
                count++;
            }
        }
        return count;
    }

    public int countIndexed(IndexedPredicate<? super T> predicate) {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (predicate.test(i, backing.get(i))) {
                count++;
            }
        }
        return count;
    }

    /* ---------- forEach / onEach ---------- */

    public void forEachIndexed(IndexedConsumer<? super T> action) {
        for (int i = 0; i < size(); i++) {
            action.accept(i, backing.get(i));
        }
    }

    public KList<T> onEach(java.util.function.Consumer<? super T> action) {
        for (int i = 0; i < size(); i++) {
            action.accept(backing.get(i));
        }
        return this; // Kotlin returns the same collection
    }

    public KList<T> onEachIndexed(IndexedConsumer<? super T> action) {
        for (int i = 0; i < size(); i++) {
            action.accept(i, backing.get(i));
        }
        return this;
    }


        /* ============================================================
       Filtering Family
       ============================================================ */

    /* ---------- filter ---------- */

    public KList<T> filter(Predicate<? super T> predicate) {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (predicate.test(e)) {
                result.add(e);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    public KList<T> filterIndexed(IndexedPredicate<? super T> predicate) {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (predicate.test(i, e)) {
                result.add(e);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    /* ---------- filterNot ---------- */

    public KList<T> filterNot(Predicate<? super T> predicate) {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (!predicate.test(e)) {
                result.add(e);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    public KList<T> filterNotIndexed(IndexedPredicate<? super T> predicate) {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (!predicate.test(i, e)) {
                result.add(e);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    /* ---------- filterNotNull ---------- */

    @SuppressWarnings("unchecked")
    public <R> KList<R> filterNotNull() {
        int size = size();
        ArrayList<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (e != null) {
                result.add((R) e);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    /* ---------- filterIsInstance ---------- */

    public <R> KList<R> filterIsInstance(Class<R> klass) {
        int size = size();
        ArrayList<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (klass.isInstance(e)) {
                result.add(klass.cast(e));
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    /* ============================================================
       Take / Drop
       ============================================================ */

    public KList<T> take(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Requested element count " + n + " is less than zero.");
        }
        int size = size();
        if (n >= size) {
            return new KList<>(new ArrayList<>(backing));
        }
        ArrayList<T> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.add(backing.get(i));
        }
        return new KList<>(result);
    }

    public KList<T> takeWhile(Predicate<? super T> predicate) {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (!predicate.test(e)) {
                break;
            }
            result.add(e);
        }
        result.trimToSize();
        return new KList<>(result);
    }

    public KList<T> takeWhileIndexed(IndexedPredicate<? super T> predicate) {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T e = backing.get(i);
            if (!predicate.test(i, e)) {
                break;
            }
            result.add(e);
        }
        result.trimToSize();
        return new KList<>(result);
    }

    public KList<T> drop(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Requested element count " + n + " is less than zero.");
        }
        int size = size();
        if (n >= size) {
            return new KList<>(new ArrayList<>(0));
        }
        int newSize = size - n;
        ArrayList<T> result = new ArrayList<>(newSize);
        for (int i = n; i < size; i++) {
            result.add(backing.get(i));
        }
        return new KList<>(result);
    }

    public KList<T> dropWhile(Predicate<? super T> predicate) {
        int size = size();
        int index = 0;
        while (index < size && predicate.test(backing.get(index))) {
            index++;
        }
        return drop(index);
    }

    public KList<T> dropWhileIndexed(IndexedPredicate<? super T> predicate) {
        int size = size();
        int index = 0;
        while (index < size && predicate.test(index, backing.get(index))) {
            index++;
        }
        return drop(index);
    }



        /* ============================================================
       Mapping Family
       ============================================================ */

    /* ---------- map ---------- */

    public <R> KList<R> map(Function<? super T, ? extends R> transform) {
        int size = size();
        ArrayList<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(transform.apply(backing.get(i)));
        }
        return new KList<>(result);
    }

    public <R> KList<R> mapIndexed(IndexedFunction<? super T, ? extends R> transform) {
        int size = size();
        ArrayList<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(transform.apply(i, backing.get(i)));
        }
        return new KList<>(result);
    }

    /* ---------- mapNotNull ---------- */

    public <R> KList<R> mapNotNull(Function<? super T, ? extends R> transform) {
        int size = size();
        ArrayList<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            R value = transform.apply(backing.get(i));
            if (value != null) {
                result.add(value);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    public <R> KList<R> mapIndexedNotNull(IndexedFunction<? super T, ? extends R> transform) {
        int size = size();
        ArrayList<R> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            R value = transform.apply(i, backing.get(i));
            if (value != null) {
                result.add(value);
            }
        }
        result.trimToSize();
        return new KList<>(result);
    }

    /* ---------- flatMap ---------- */

    public <R> KList<R> flatMap(Function<? super T, ? extends Iterable<? extends R>> transform) {
        ArrayList<R> result = new ArrayList<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            Iterable<? extends R> iterable = transform.apply(backing.get(i));
            for (R r : iterable) {
                result.add(r);
            }
        }
        return new KList<>(result);
    }

    public <R> KList<R> flatMapIndexed(IndexedFunction<? super T, ? extends Iterable<? extends R>> transform) {
        ArrayList<R> result = new ArrayList<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            Iterable<? extends R> iterable = transform.apply(i, backing.get(i));
            for (R r : iterable) {
                result.add(r);
            }
        }
        return new KList<>(result);
    }

    /* ---------- flatten ---------- */

    @SuppressWarnings("unchecked")
    public <R> KList<R> flatten() {
        ArrayList<R> result = new ArrayList<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            Iterable<? extends R> iterable = (Iterable<? extends R>) backing.get(i);
            for (R r : iterable) {
                result.add(r);
            }
        }
        return new KList<>(result);
    }



        /* ============================================================
       Association Family
       ============================================================ */

    /* ---------- associate ---------- */

    public <K, V> LinkedHashMap<K, V> associate(
            Function<? super T, ? extends Map.Entry<? extends K, ? extends V>> transform) {

        LinkedHashMap<K, V> result = new LinkedHashMap<>(size());
        for (int i = 0; i < size(); i++) {
            Map.Entry<? extends K, ? extends V> entry = transform.apply(backing.get(i));
            result.put(entry.getKey(), entry.getValue()); // last wins
        }
        return result;
    }

    /* ---------- associateBy ---------- */

    public <K> LinkedHashMap<K, T> associateBy(
            Function<? super T, ? extends K> keySelector) {

        LinkedHashMap<K, T> result = new LinkedHashMap<>(size());
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            result.put(keySelector.apply(e), e); // last wins
        }
        return result;
    }

    public <K, V> LinkedHashMap<K, V> associateBy(
            Function<? super T, ? extends K> keySelector,
            Function<? super T, ? extends V> valueTransform) {

        LinkedHashMap<K, V> result = new LinkedHashMap<>(size());
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            result.put(keySelector.apply(e), valueTransform.apply(e));
        }
        return result;
    }

    /* ---------- associateWith ---------- */

    public <V> LinkedHashMap<T, V> associateWith(
            Function<? super T, ? extends V> valueSelector) {

        LinkedHashMap<T, V> result = new LinkedHashMap<>(size());
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            result.put(e, valueSelector.apply(e));
        }
        return result;
    }

    public <V> LinkedHashMap<T, V> associateWithIndexed(
            IndexedFunction<? super T, ? extends V> valueSelector) {

        LinkedHashMap<T, V> result = new LinkedHashMap<>(size());
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            result.put(e, valueSelector.apply(i, e));
        }
        return result;
    }



        /* ============================================================
       Fold Family
       ============================================================ */

    public <R> R fold(R initial, BiFunction<? super R, ? super T, ? extends R> operation) {
        R acc = initial;
        for (int i = 0; i < size(); i++) {
            acc = operation.apply(acc, backing.get(i));
        }
        return acc;
    }

    public <R> R foldIndexed(R initial,
                             IndexedBiFunction<? super R, ? super T, ? extends R> operation) {

        R acc = initial;
        for (int i = 0; i < size(); i++) {
            acc = operation.apply(i, acc, backing.get(i));
        }
        return acc;
    }

    public <R> KList<R> runningFold(R initial,
                                    BiFunction<? super R, ? super T, ? extends R> operation) {

        int size = size();
        ArrayList<R> result = new ArrayList<>(size + 1);
        R acc = initial;
        result.add(acc);

        for (int i = 0; i < size; i++) {
            acc = operation.apply(acc, backing.get(i));
            result.add(acc);
        }

        return new KList<>(result);
    }

    public <R> KList<R> runningFoldIndexed(R initial,
                                           IndexedBiFunction<? super R, ? super T, ? extends R> operation) {

        int size = size();
        ArrayList<R> result = new ArrayList<>(size + 1);
        R acc = initial;
        result.add(acc);

        for (int i = 0; i < size; i++) {
            acc = operation.apply(i, acc, backing.get(i));
            result.add(acc);
        }

        return new KList<>(result);
    }



        /* ============================================================
       Reduce Family
       ============================================================ */

    public T reduce(BiFunction<? super T, ? super T, ? extends T> operation) {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty collection can't be reduced.");
        }

        T acc = backing.get(0);
        for (int i = 1; i < size(); i++) {
            acc = operation.apply(acc, backing.get(i));
        }
        return acc;
    }

    public T reduceIndexed(
            IndexedBiFunction<? super T, ? super T, ? extends T> operation) {

        if (isEmpty()) {
            throw new NoSuchElementException("Empty collection can't be reduced.");
        }

        T acc = backing.get(0);
        for (int i = 1; i < size(); i++) {
            acc = operation.apply(i, acc, backing.get(i));
        }
        return acc;
    }

    public T reduceOrNull(BiFunction<? super T, ? super T, ? extends T> operation) {
        if (isEmpty()) return null;

        T acc = backing.get(0);
        for (int i = 1; i < size(); i++) {
            acc = operation.apply(acc, backing.get(i));
        }
        return acc;
    }

    public T reduceIndexedOrNull(
            IndexedBiFunction<? super T, ? super T, ? extends T> operation) {

        if (isEmpty()) return null;

        T acc = backing.get(0);
        for (int i = 1; i < size(); i++) {
            acc = operation.apply(i, acc, backing.get(i));
        }
        return acc;
    }

    public KList<T> runningReduce(
            BiFunction<? super T, ? super T, ? extends T> operation) {

        if (isEmpty()) {
            return new KList<>(new ArrayList<>(0));
        }

        int size = size();
        ArrayList<T> result = new ArrayList<>(size);
        T acc = backing.get(0);
        result.add(acc);

        for (int i = 1; i < size; i++) {
            acc = operation.apply(acc, backing.get(i));
            result.add(acc);
        }

        return new KList<>(result);
    }




        /* ============================================================
       sumOf Family
       ============================================================ */

    public int sumOfInt(ToIntFunction<? super T> selector) {
        int sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += selector.applyAsInt(backing.get(i));
        }
        return sum;
    }

    public long sumOfLong(ToLongFunction<? super T> selector) {
        long sum = 0L;
        for (int i = 0; i < size(); i++) {
            sum += selector.applyAsLong(backing.get(i));
        }
        return sum;
    }

    public double sumOfDouble(ToDoubleFunction<? super T> selector) {
        double sum = 0.0;
        for (int i = 0; i < size(); i++) {
            sum += selector.applyAsDouble(backing.get(i));
        }
        return sum;
    }


    /* ============================================================
       Min / Max
       ============================================================ */

    public <R extends Comparable<R>> T maxBy(
            Function<? super T, ? extends R> selector) {

        if (isEmpty()) {
            throw new NoSuchElementException("Collection is empty.");
        }

        T maxElem = backing.get(0);
        R maxValue = selector.apply(maxElem);

        for (int i = 1; i < size(); i++) {
            T e = backing.get(i);
            R value = selector.apply(e);
            if (value.compareTo(maxValue) > 0) {
                maxElem = e;
                maxValue = value;
            }
        }

        return maxElem;
    }

    public <R extends Comparable<R>> T maxByOrNull(
            Function<? super T, ? extends R> selector) {

        if (isEmpty()) return null;
        return maxBy(selector);
    }

    public <R extends Comparable<R>> T minBy(
            Function<? super T, ? extends R> selector) {

        if (isEmpty()) {
            throw new NoSuchElementException("Collection is empty.");
        }

        T minElem = backing.get(0);
        R minValue = selector.apply(minElem);

        for (int i = 1; i < size(); i++) {
            T e = backing.get(i);
            R value = selector.apply(e);
            if (value.compareTo(minValue) < 0) {
                minElem = e;
                minValue = value;
            }
        }

        return minElem;
    }

    public <R extends Comparable<R>> T minByOrNull(
            Function<? super T, ? extends R> selector) {

        if (isEmpty()) return null;
        return minBy(selector);
    }

    public <R extends Comparable<R>> R maxOf(
            Function<? super T, ? extends R> selector) {

        return selector.apply(maxBy(selector));
    }

    public <R extends Comparable<R>> R minOf(
            Function<? super T, ? extends R> selector) {

        return selector.apply(minBy(selector));
    }

    /* ============================================================
       Distinct
       ============================================================ */

    public KList<T> distinct() {
        LinkedHashSet<T> seen = new LinkedHashSet<>(size());
        ArrayList<T> result = new ArrayList<>(size());

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (seen.add(e)) {
                result.add(e);
            }
        }

        result.trimToSize();
        return new KList<>(result);
    }

    public <K> KList<T> distinctBy(
            Function<? super T, ? extends K> selector) {

        LinkedHashSet<K> seen = new LinkedHashSet<>(size());
        ArrayList<T> result = new ArrayList<>(size());

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            K key = selector.apply(e);
            if (seen.add(key)) {
                result.add(e);
            }
        }

        result.trimToSize();
        return new KList<>(result);
    }

    /* ============================================================
       Set Algebra
       ============================================================ */

    public KList<T> union(Iterable<? extends T> other) {
        LinkedHashSet<T> set = new LinkedHashSet<>(size());
        for (int i = 0; i < size(); i++) {
            set.add(backing.get(i));
        }
        for (T e : other) {
            set.add(e);
        }
        return new KList<>(new ArrayList<>(set));
    }

    public KList<T> intersect(Iterable<? extends T> other) {
        LinkedHashSet<T> otherSet = new LinkedHashSet<>();
        for (T e : other) {
            otherSet.add(e);
        }

        ArrayList<T> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (otherSet.contains(e)) {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

    public KList<T> subtract(Iterable<? extends T> other) {
        LinkedHashSet<T> otherSet = new LinkedHashSet<>();
        for (T e : other) {
            otherSet.add(e);
        }

        ArrayList<T> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (!otherSet.contains(e)) {
                result.add(e);
            }
        }
        return new KList<>(result);
    }

    public KList<T> plus(T element) {
        ArrayList<T> result = new ArrayList<>(size() + 1);
        result.addAll(backing);
        result.add(element);
        return new KList<>(result);
    }

    public KList<T> plus(Iterable<? extends T> elements) {
        ArrayList<T> result = new ArrayList<>(size());
        result.addAll(backing);
        for (T e : elements) {
            result.add(e);
        }
        return new KList<>(result);
    }

    public KList<T> minus(T element) {
        ArrayList<T> result = new ArrayList<>(size());
        boolean removed = false;

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (!removed && Objects.equals(e, element)) {
                removed = true;
                continue;
            }
            result.add(e);
        }

        return new KList<>(result);
    }

    public KList<T> minus(Iterable<? extends T> elements) {
        LinkedHashSet<T> removeSet = new LinkedHashSet<>();
        for (T e : elements) {
            removeSet.add(e);
        }

        ArrayList<T> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            if (!removeSet.contains(e)) {
                result.add(e);
            }
        }

        return new KList<>(result);
    }


        /* ============================================================
       Ordering
       ============================================================ */

    public KList<T> reversed() {
        int size = size();
        ArrayList<T> result = new ArrayList<>(size);

        for (int i = size - 1; i >= 0; i--) {
            result.add(backing.get(i));
        }

        return new KList<>(result);
    }

    @SuppressWarnings("unchecked")
    public KList<T> sorted() {
        ArrayList<T> result = new ArrayList<>(backing);
        result.sort((Comparator<? super T>) Comparator.naturalOrder());
        return new KList<>(result);
    }

    @SuppressWarnings("unchecked")
    public KList<T> sortedDescending() {
        ArrayList<T> result = new ArrayList<>(backing);
        result.sort((Comparator<? super T>) Comparator.reverseOrder());
        return new KList<>(result);
    }

    public <R extends Comparable<R>> KList<T> sortedBy(
            Function<? super T, ? extends R> selector) {

        ArrayList<T> result = new ArrayList<>(backing);
        result.sort(Comparator.comparing(selector));
        return new KList<>(result);
    }


        /* ============================================================
       Zip / Chunked / Windowed
       ============================================================ */

    public <R> KList<Map.Entry<T, R>> zip(Iterable<? extends R> other) {
        Iterator<? extends R> it = other.iterator();
        ArrayList<Map.Entry<T, R>> result = new ArrayList<>();

        for (int i = 0; i < size() && it.hasNext(); i++) {
            result.add(Map.entry(backing.get(i), it.next()));
        }

        return new KList<>(result);
    }

    public KList<KList<T>> chunked(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive.");
        }

        ArrayList<KList<T>> result = new ArrayList<>();
        int total = size();

        for (int i = 0; i < total; i += size) {
            int end = Math.min(i + size, total);
            ArrayList<T> chunk = new ArrayList<>(end - i);
            for (int j = i; j < end; j++) {
                chunk.add(backing.get(j));
            }
            result.add(new KList<>(chunk));
        }

        return new KList<>(result);
    }

    public KList<KList<T>> windowed(int size, int step, boolean partialWindows) {
        if (size <= 0 || step <= 0) {
            throw new IllegalArgumentException("size and step must be positive.");
        }

        ArrayList<KList<T>> result = new ArrayList<>();
        int total = size();

        for (int i = 0; i < total; i += step) {
            int end = i + size;
            if (end > total) {
                if (!partialWindows) break;
                end = total;
            }

            ArrayList<T> window = new ArrayList<>(end - i);
            for (int j = i; j < end; j++) {
                window.add(backing.get(j));
            }

            result.add(new KList<>(window));
        }

        return new KList<>(result);
    }


        /* ============================================================
       Binary Search (Comparable)
       ============================================================ */

    @SuppressWarnings("unchecked")
    public int binarySearch(T element) {
        return binarySearch(element, 0, size());
    }

    @SuppressWarnings("unchecked")
    public int binarySearch(T element, int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);

        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = backing.get(mid);
            int cmp = ((Comparable<? super T>) midVal).compareTo(element);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // exact match
            }
        }

        return -(low + 1); // insertion point encoding (Kotlin parity)
    }


    public int binarySearch(
            T element,
            Comparator<? super T> comparator) {

        return binarySearch(element, 0, size(), comparator);
    }

    public int binarySearch(
            T element,
            int fromIndex,
            int toIndex,
            Comparator<? super T> comparator) {

        checkRange(fromIndex, toIndex);

        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = backing.get(mid);
            int cmp = comparator.compare(midVal, element);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -(low + 1);
    }


    public <K extends Comparable<K>> int binarySearchBy(
            K key,
            Function<? super T, ? extends K> selector) {

        return binarySearchBy(key, 0, size(), selector);
    }

    public <K extends Comparable<K>> int binarySearchBy(
            K key,
            int fromIndex,
            int toIndex,
            Function<? super T, ? extends K> selector) {

        checkRange(fromIndex, toIndex);

        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = backing.get(mid);
            K midKey = selector.apply(midVal);

            int cmp = midKey.compareTo(key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -(low + 1);
    }

    private void checkRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(
                    "fromIndex: " + fromIndex + ", toIndex: " + toIndex);
        }
    }

    /* ============================================================
       Indices
       ============================================================ */

    public KList<Integer> indices() {
        int size = size();
        ArrayList<Integer> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(i);
        }
        return new KList<>(result);
    }


        /* ============================================================
       Reversed View (Non-Copy)
       ============================================================ */

    public KList<T> asReversedView() {
        return new KList<>(new ReversedView<>(backing));
    }

    private static final class ReversedView<E>
            extends AbstractList<E>
            implements RandomAccess {

        private final List<E> original;

        ReversedView(List<E> original) {
            this.original = original;
        }

        @Override
        public E get(int index) {
            int size = size();
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException();
            }
            return original.get(size - 1 - index);
        }

        @Override
        public int size() {
            return original.size();
        }
    }


        /* ============================================================
       withIndex
       ============================================================ */

    public KList<Map.Entry<Integer, T>> withIndex() {
        int size = size();
        ArrayList<Map.Entry<Integer, T>> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            result.add(Map.entry(i, backing.get(i)));
        }

        return new KList<>(result);
    }

    /* ============================================================
       Conversion
       ============================================================ */

    public KList<T> toList() {
        return new KList<>(new ArrayList<>(backing));
    }

    public ArrayList<T> toMutableList() {
        return new ArrayList<>(backing);
    }


        /* ============================================================
       Grouping
       ============================================================ */

    public <K> LinkedHashMap<K, KList<T>> groupBy(
            Function<? super T, ? extends K> keySelector) {

        LinkedHashMap<K, ArrayList<T>> temp = new LinkedHashMap<>();

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            K key = keySelector.apply(e);

            temp.computeIfAbsent(key, k -> new ArrayList<>()).add(e);
        }

        LinkedHashMap<K, KList<T>> result = new LinkedHashMap<>(temp.size());
        for (Map.Entry<K, ArrayList<T>> entry : temp.entrySet()) {
            result.put(entry.getKey(), new KList<>(entry.getValue()));
        }

        return result;
    }

    public <K, V> LinkedHashMap<K, KList<V>> groupBy(
            Function<? super T, ? extends K> keySelector,
            Function<? super T, ? extends V> valueTransform) {

        LinkedHashMap<K, ArrayList<V>> temp = new LinkedHashMap<>();

        for (int i = 0; i < size(); i++) {
            T e = backing.get(i);
            K key = keySelector.apply(e);
            V value = valueTransform.apply(e);

            temp.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        LinkedHashMap<K, KList<V>> result = new LinkedHashMap<>(temp.size());
        for (Map.Entry<K, ArrayList<V>> entry : temp.entrySet()) {
            result.put(entry.getKey(), new KList<>(entry.getValue()));
        }

        return result;
    }




}
