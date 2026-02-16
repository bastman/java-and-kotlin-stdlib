package org.example.kcollections.full.v2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class KListTest {

    /* ============================================================
       Helpers
       ============================================================ */

    private KList<Integer> ints(Integer... values) {
        return KList.of(Arrays.asList(values));
    }

    private KList<String> strings(String... values) {
        return KList.of(Arrays.asList(values));
    }

    /* ============================================================
       Retrieval
       ============================================================ */

    @Test
    void first_and_last() {
        KList<Integer> list = ints(1, 2, 3);

        assertEquals(1, list.first());
        assertEquals(3, list.last());
    }

    @Test
    void first_empty_throws() {
        assertThrows(NoSuchElementException.class,
                () -> ints().first());
    }

    @Test
    void single_behavior() {
        assertEquals(5, ints(5).single());

        assertThrows(NoSuchElementException.class,
                () -> ints().single());

        assertThrows(IllegalArgumentException.class,
                () -> ints(1, 2).single());
    }

    @Test
    void getOrNull() {
        KList<Integer> list = ints(1, 2);
        assertNull(list.getOrNull(-1));
        assertNull(list.getOrNull(10));
        assertEquals(2, list.getOrNull(1));
    }

    /* ============================================================
       Predicate Family
       ============================================================ */

    @Test
    void all_any_none_semantics() {
        KList<Integer> list = ints(1, 2, 3);

        assertTrue(list.all(i -> i > 0));
        assertTrue(list.any(i -> i == 2));
        assertFalse(list.none(i -> i == 2));

        assertTrue(ints().all(i -> true));
        assertFalse(ints().any());
        assertTrue(ints().none());
    }

    @Test
    void short_circuit_behavior() {
        AtomicInteger counter = new AtomicInteger();

        KList<Integer> list = ints(1, 2, 3);

        list.any(i -> {
            counter.incrementAndGet();
            return true;
        });

        assertEquals(1, counter.get());
    }

    /* ============================================================
       Filtering
       ============================================================ */

    @Test
    void filter_variants() {
        KList<Integer> list = ints(1, 2, 3, 4);

        assertEquals(List.of(2, 4),
                list.filter(i -> i % 2 == 0).toMutableList());

        assertEquals(List.of(1, 3),
                list.filterNot(i -> i % 2 == 0).toMutableList());
    }

    @Test
    void filterNotNull() {
        KList<String> list = strings("a", null, "b");

        assertEquals(List.of("a", "b"),
                list.filterNotNull().toMutableList());
    }

    /* ============================================================
       Mapping
       ============================================================ */

    @Test
    void map_and_flatMap() {
        KList<Integer> list = ints(1, 2, 3);

        assertEquals(List.of(2, 4, 6),
                list.map(i -> i * 2).toMutableList());

        assertEquals(List.of(1, 1, 2, 2, 3, 3),
                list.flatMap(i -> List.of(i, i)).toMutableList());
    }

    @Test
    void flatten() {
        KList<List<Integer>> nested =
                KList.of(List.of(List.of(1, 2), List.of(3)));

        assertEquals(List.of(1, 2, 3),
                nested.flatten().toMutableList());
    }

    /* ============================================================
       Association & Grouping
       ============================================================ */

    @Test
    void associate_last_wins() {
        KList<String> list = strings("a", "bb", "c");

        Map<Integer, String> map =
                list.associateBy(String::length);

        assertEquals("c", map.get(1)); // last wins
    }

    @Test
    void groupBy_preserves_order() {
        KList<String> list = strings("a", "bb", "c");

        Map<Integer, KList<String>> grouped =
                list.groupBy(String::length);

        assertEquals(List.of("a", "c"),
                grouped.get(1).toMutableList());
    }

    /* ============================================================
       Aggregation
       ============================================================ */

    @Test
    void reduce_and_fold() {
        KList<Integer> list = ints(1, 2, 3);

        assertEquals(6, list.reduce(Integer::sum));
        assertEquals(7, list.fold(1, Integer::sum));

        assertThrows(NoSuchElementException.class,
                () -> ints().reduce(Integer::sum));
    }

    @Test
    void runningFold_size() {
        KList<Integer> list = ints(1, 2);

        assertEquals(List.of(0, 1, 3),
                list.runningFold(0, Integer::sum).toMutableList());
    }

    @Test
    void sumOf_variants() {
        KList<Integer> list = ints(1, 2, 3);

        assertEquals(6, list.sumOfInt(i -> i));
        assertEquals(6L, list.sumOfLong(i -> i));
        assertEquals(6.0, list.sumOfDouble(i -> i));
    }

    /* ============================================================
       Distinct & Set Algebra
       ============================================================ */

    @Test
    void distinct_first_wins() {
        KList<Integer> list = ints(1, 2, 1, 3);

        assertEquals(List.of(1, 2, 3),
                list.distinct().toMutableList());
    }

    @Test
    void minus_removes_first_only() {
        KList<Integer> list = ints(1, 2, 1);

        assertEquals(List.of(2, 1),
                list.minus(1).toMutableList());
    }

    @Test
    void union_preserves_order() {
        KList<Integer> list = ints(1, 2);

        assertEquals(List.of(1, 2, 3),
                list.union(List.of(2, 3)).toMutableList());
    }

    /* ============================================================
       Ordering
       ============================================================ */

    @Test
    void sorted_and_reversed() {
        KList<Integer> list = ints(3, 1, 2);

        assertEquals(List.of(1, 2, 3),
                list.sorted().toMutableList());

        assertEquals(List.of(2, 1, 3),
                list.reversed().toMutableList());
    }

    /* ============================================================
       Zip / Chunked / Windowed
       ============================================================ */

    @Test
    void zip_truncates() {
        KList<Integer> list = ints(1, 2, 3);

        var zipped = list.zip(List.of("a", "b"));

        assertEquals(2, zipped.size());
    }

    @Test
    void chunked() {
        KList<Integer> list = ints(1, 2, 3, 4);

        assertEquals(2,
                list.chunked(2).size());
    }

    @Test
    void windowed_partial() {
        KList<Integer> list = ints(1, 2, 3);

        assertEquals(2,
                list.windowed(2, 1, true).size());
    }

    /* ============================================================
       Binary Search
       ============================================================ */

    @Test
    void binarySearch_found() {
        KList<Integer> list = ints(1, 2, 3, 4);

        assertEquals(2,
                list.binarySearch(3));
    }

    @Test
    void binarySearch_not_found_insertion_point() {
        KList<Integer> list = ints(1, 3, 5);

        int result = list.binarySearch(4);

        assertEquals(-3, result); // insertion at index 2 → -(2+1)
    }

    /* ============================================================
       Reversed View
       ============================================================ */

    @Test
    void reversed_view_reflects_original() {
        List<Integer> base = new ArrayList<>(List.of(1, 2, 3));
        KList<Integer> list = KList.of(base);

        KList<Integer> reversed = list.asReversedView();

        assertEquals(List.of(3, 2, 1),
                reversed.toMutableList());

        base.add(4);

        assertEquals(4, reversed.get(0));
    }

    /* ============================================================
       Indices
       ============================================================ */

    @Test
    void indices() {
        assertEquals(List.of(0, 1, 2),
                ints(1, 2, 3).indices().toMutableList());
    }
}
