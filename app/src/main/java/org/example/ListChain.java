package org.example;

import kotlin.collections.CollectionsKt;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Fluent, eager, null-safe list pipeline for Java,
 * backed by kotlin-stdlib collection utilities.
 * <p>
 * Semantics:
 * - Input list may be null (treated as empty)
 * - Null elements are dropped eagerly
 * - Mapping functions may return null (dropped)
 * - Never holds a null list internally
 */
@NullMarked
public final class ListChain<T> {

    private final List<T> list;

    private ListChain(List<T> list) {
        this.list = list;
    }

    /* ============================================================
     * Factory
     * ============================================================ */

    public static <T> ListChain<T> of(@Nullable List<? extends T> list) {
        if (list == null) {
            return new ListChain<>(Collections.emptyList());
        }
        return new ListChain<>(
                CollectionsKt.filterNotNull(list)
        );
    }

    /* ============================================================
     * Mapping
     * ============================================================ */

    public <R> ListChain<R> map(
            Function<? super T, @Nullable ? extends R> fn
    ) {
        return new ListChain<>(
                CollectionsKt.mapNotNull(list, fn::apply)
        );
    }

    public <R> ListChain<R> mapNotNull(
            Function<? super T, @Nullable ? extends R> fn
    ) {
        return new ListChain<>(
                CollectionsKt.mapNotNull(list, fn::apply)
        );
    }

    public <R> ListChain<R> flatMap(
            Function<? super T, @Nullable ? extends Collection<? extends R>> fn
    ) {
        return new ListChain<>(
                CollectionsKt.filterNotNull(
                        CollectionsKt.flatMap(
                                list,
                                t -> {
                                    Collection<? extends R> r = fn.apply(t);
                                    return r == null ? Collections.emptyList() : r;
                                }
                        )
                )
        );
    }

    public <R> ListChain<R> flatMapNotNull(
            Function<? super T, @Nullable ? extends Collection<? extends R>> fn
    ) {
        return flatMap(fn);
    }

    /* ============================================================
     * Filtering
     * ============================================================ */

    public ListChain<T> filter(Predicate<? super T> predicate) {
        return new ListChain<>(
                CollectionsKt.filter(list, predicate::test)
        );
    }

    public ListChain<T> filterNot(Predicate<? super T> predicate) {
        return new ListChain<>(
                CollectionsKt.filterNot(list, predicate::test)
        );
    }

    /* ============================================================
     * Distinct / sorting
     * ============================================================ */

    public ListChain<T> distinct() {
        return new ListChain<>(CollectionsKt.distinct(list));
    }

    public <K> ListChain<T> distinctBy(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return new ListChain<>(
                CollectionsKt.distinctBy(list, keySelector::apply)
        );
    }

    /*
    public ListChain<T> sorted() {
        return new ListChain<>(CollectionsKt.sorted(list));
    }

     */

    public <K extends Comparable<K>> ListChain<T> sortedBy(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return new ListChain<>(
                CollectionsKt.sortedBy(list, keySelector::apply)
        );
    }

    public <K extends Comparable<K>> ListChain<T> sortedByDescending(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return new ListChain<>(
                CollectionsKt.sortedByDescending(list, keySelector::apply)
        );
    }

    /* ============================================================
     * Grouping / association
     * ============================================================ */

    public <K> Map<K, List<T>> groupBy(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return CollectionsKt.groupBy(list, keySelector::apply);
    }

    public <K> Map<K, T> associateBy(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return CollectionsKt.associateBy(list, keySelector::apply);
    }

    public <K, V> Map<K, V> associateBy(
            Function<? super T, @Nullable ? extends K> keySelector,
            Function<? super T, @Nullable ? extends V> valueTransform
    ) {
        return CollectionsKt.associateBy(
                list,
                keySelector::apply,
                valueTransform::apply
        );
    }

    public <V> Map<T, V> associateWith(
            Function<? super T, @Nullable ? extends V> valueSelector
    ) {
        return CollectionsKt.associateWith(list, valueSelector::apply);
    }

    /*
    public Map<Boolean, List<T>> partition(
            Predicate<? super T> predicate
    ) {
        return CollectionsKt.partition(list, predicate::test);
    }

     */

    /* ============================================================
     * Slicing
     * ============================================================ */

    public ListChain<T> take(int n) {
        return new ListChain<>(CollectionsKt.take(list, n));
    }

    public ListChain<T> takeLast(int n) {
        return new ListChain<>(CollectionsKt.takeLast(list, n));
    }

    public ListChain<T> drop(int n) {
        return new ListChain<>(CollectionsKt.drop(list, n));
    }

    public ListChain<T> dropLast(int n) {
        return new ListChain<>(CollectionsKt.dropLast(list, n));
    }

    public ListChain<List<T>> chunked(int size) {
        return new ListChain<>(CollectionsKt.chunked(list, size));
    }

    /* ============================================================
     * Queries
     * ============================================================ */

    public boolean any(Predicate<? super T> predicate) {
        return CollectionsKt.any(list, predicate::test);
    }

    public boolean all(Predicate<? super T> predicate) {
        return CollectionsKt.all(list, predicate::test);
    }

    public boolean none(Predicate<? super T> predicate) {
        return CollectionsKt.none(list, predicate::test);
    }

    public int count(Predicate<? super T> predicate) {
        return CollectionsKt.count(list, predicate::test);
    }

    /* ============================================================
     * Folding
     * ============================================================ */

    public <R> R fold(
            R initial,
            BiFunction<R, T, R> operation
    ) {
        return CollectionsKt.fold(
                list,
                initial,
                (acc, t) -> operation.apply(acc, t)
        );
    }

    /* ============================================================
     * Side effects
     * ============================================================ */
/*
    public ListChain<T> onEach(Consumer<? super T> action) {
        CollectionsKt.forEach(list, action::accept);
        return this;
    }

 */

    /* ============================================================
     * Terminals
     * ============================================================ */

    public T first() {
        return CollectionsKt.first(list);
    }

    public @Nullable T firstOrNull() {
        return CollectionsKt.firstOrNull(list);
    }

    public Optional<T> firstOptional() {
        return Optional.ofNullable(firstOrNull());
    }

    public List<T> toList() {
        return list;
    }

    public Set<T> toSet() {
        return new LinkedHashSet<>(list);
    }


    public String joinToString(
            CharSequence separator,
            CharSequence prefix,
            CharSequence postfix,
            @Nullable Integer limit,
            @Nullable CharSequence truncated
    ) {
        var _limit = (limit != null) ? limit : -1;
        var _truncated = (truncated != null) ? truncated : "...";
        return CollectionsKt.joinToString(
                list, separator, prefix, postfix, _limit, _truncated, null
        );
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public ListChain<T> reversed() {
        return new ListChain<>(CollectionsKt.reversed(list));
    }
}