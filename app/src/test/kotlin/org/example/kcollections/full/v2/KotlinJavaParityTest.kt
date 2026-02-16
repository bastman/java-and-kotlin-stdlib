package org.example.kcollections.full.v2

import org.junit.jupiter.api.Test

class KotlinJavaParityTest {

    /* ============================================================
       Retrieval
       ============================================================ */

    @Test
    fun retrieval_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare("first", list,
            { first() },
            { first() })

        ParityHarness.compare("last", list,
            { last() },
            { last() })

        ParityHarness.compare("getOrNull", list,
            { getOrNull(10) },
            { getOrNull(10) })

        ParityHarness.compare("single success", listOf(42),
            { single() },
            { single() })

        ParityHarness.compare("single failure", list,
            { single() },
            { single() })
    }

    /* ============================================================
       Predicate Family
       ============================================================ */

    @Test
    fun predicate_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare("all", list,
            { all { it > 0 } },
            { all { it > 0 } })

        ParityHarness.compare("any", list,
            { any { it == 2 } },
            { any { it == 2 } })

        ParityHarness.compare("none", list,
            { none { it == 4 } },
            { none { it == 4 } })
    }

    /* ============================================================
       Filtering
       ============================================================ */

    @Test
    fun filtering_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare("filter", list,
            { filter { it % 2 == 0 } },
            { filter { it % 2 == 0 }.toMutableList() })

        ParityHarness.compare("filterNot", list,
            { filterNot { it % 2 == 0 } },
            { filterNot { it % 2 == 0 }.toMutableList() })
    }

    /* ============================================================
       Mapping
       ============================================================ */

    @Test
    fun mapping_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare("map", list,
            { map { it * 2 } },
            { map { it * 2 }.toMutableList() })

        ParityHarness.compare("flatMap", list,
            { flatMap { listOf(it, it) } },
            { flatMap { listOf(it, it) }.toMutableList() })
    }

    /* ============================================================
       Association / Grouping
       ============================================================ */

    @Test
    fun associate_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare("associateBy", list,
            { associateBy { it.length } },
            { associateBy { it.length } })
    }

    @Test
    fun groupBy_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare("groupBy", list,
            { groupBy { it.length } },
            { groupBy { it.length } })
    }

    /* ============================================================
       Aggregation
       ============================================================ */

    @Test
    fun reduce_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare("reduce", list,
            { reduce(Int::plus) },
            { reduce(Integer::sum) })

        ParityHarness.compare("reduce empty", emptyList<Int>(),
            { reduce(Int::plus) },
            { reduce(Integer::sum) })
    }

    @Test
    fun sumOf_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare("sumOf", list,
            { sumOf { it } },
            { sumOfInt { it } })
    }

    /* ============================================================
       Distinct / Set Algebra
       ============================================================ */

    @Test
    fun distinct_parity() {
        val list = listOf(1, 2, 1, 3)

        ParityHarness.compare("distinct", list,
            { distinct() },
            { distinct().toMutableList() })
    }

    @Test
    fun union_parity() {
        val list = listOf(1, 2)

        ParityHarness.compare("union", list,
            { union(listOf(2, 3)).toList() },
            { union(listOf(2, 3)).toMutableList() })
    }

    /* ============================================================
       Ordering
       ============================================================ */

    @Test
    fun sorted_parity() {
        val list = listOf(3, 1, 2)

        ParityHarness.compare("sorted", list,
            { sorted() },
            { sorted().toMutableList() })
    }

    /* ============================================================
       Binary Search
       ============================================================ */

    @Test
    fun binary_search_parity() {
        val list = listOf(1, 3, 5)

        ParityHarness.compare("binarySearch found", list,
            { binarySearch(3) },
            { binarySearch(3) })

        ParityHarness.compare("binarySearch insertion", list,
            { binarySearch(4) },
            { binarySearch(4) })
    }
}