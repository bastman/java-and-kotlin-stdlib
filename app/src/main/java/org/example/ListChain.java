package org.example;

import kotlin.collections.CollectionsKt;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;

@NullMarked
public final class ListChain<T> {

    private final List<T> list;

    private ListChain(List<T> list) {
        this.list = list;
    }

    // ---------- factory ----------

    public static <T> ListChain<T> of(@Nullable List<? extends T> list) {
        if (list == null) {
            return new ListChain<>(Collections.emptyList());
        }
        return new ListChain<>(
                CollectionsKt.filterNotNull(list)
        );
    }

    // ---------- transforms ----------

    public <R> ListChain<R> map(
            Function<? super T, @Nullable ? extends R> fn
    ) {
        return new ListChain<>(
                CollectionsKt.filterNotNull(
                        CollectionsKt.map(list, fn::apply)
                )
        );
    }

    public ListChain<T> filter(
            Predicate<? super T> predicate
    ) {
        return new ListChain<>(
                CollectionsKt.filter(list, predicate::test)
        );
    }

    // ---------- flatMap ----------

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

    // ---------- distinct / sort ----------

    public <K> ListChain<T> distinctBy(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return new ListChain<>(
                CollectionsKt.distinctBy(list, keySelector::apply)
        );
    }

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

    // ---------- side effects ----------
/*
    public ListChain<T> onEach(
            Consumer<? super T> action
    ) {
        CollectionsKt.forEach(list, action::accept);
        return this;
    }

 */

    // ---------- terminals ----------

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

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
    public <K> Map<K, List<T>> groupBy(
            Function<? super T, @Nullable ? extends K> keySelector
    ) {
        return CollectionsKt.groupBy(list, keySelector::apply);
    }
}
