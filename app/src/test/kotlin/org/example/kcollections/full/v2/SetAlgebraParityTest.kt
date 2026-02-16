package org.example.kcollections.full.v2

import org.junit.jupiter.api.Test

class SetAlgebraParityTest {

    /* ============================================================
       union(Iterable)
       ============================================================ */

    @Test
    fun union_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "union",
                Iterable::class.java
            ),
            list,
            { union(listOf(3, 4)) },
            { union(listOf(3, 4)).toMutableList() }
        )
    }

    /* ============================================================
       intersect(Iterable)
       ============================================================ */

    @Test
    fun intersect_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "intersect",
                Iterable::class.java
            ),
            list,
            { intersect(listOf(2, 3)) },
            { intersect(listOf(2, 3)).toMutableList() }
        )
    }

    /* ============================================================
       subtract(Iterable)
       ============================================================ */

    @Test
    fun subtract_parity() {
        val list = listOf(1, 2, 3, 2)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "subtract",
                Iterable::class.java
            ),
            list,
            { subtract(listOf(2)) },
            { subtract(listOf(2)).toMutableList() }
        )
    }

    /* ============================================================
       plus(element)
       ============================================================ */

    @Test
    fun plus_element_parity() {
        val list = listOf(1, 2)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "plus",
                Any::class.java
            ),
            list,
            { plus(3) },
            { plus(3).toMutableList() }
        )
    }

    /* ============================================================
       plus(Iterable)
       ============================================================ */

    @Test
    fun plus_iterable_parity() {
        val list = listOf(1, 2)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "plus",
                Iterable::class.java
            ),
            list,
            { plus(listOf(3, 4)) },
            { plus(listOf(3, 4)).toMutableList() }
        )
    }

    /* ============================================================
       minus(element)
       ============================================================ */

    @Test
    fun minus_element_parity() {
        val list = listOf(1, 2, 1, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "minus",
                Any::class.java
            ),
            list,
            { minus(1) },
            { minus(1).toMutableList() }
        )
    }

    /* ============================================================
       minus(Iterable)
       ============================================================ */

    @Test
    fun minus_iterable_parity() {
        val list = listOf(1, 2, 1, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "minus",
                Iterable::class.java
            ),
            list,
            { minus(listOf(1)) },
            { minus(listOf(1)).toMutableList() }
        )
    }

    /* ============================================================
       Order Preservation
       ============================================================ */

    @Test
    fun order_preservation_parity() {
        val list = listOf(3, 1, 2)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "union",
                Iterable::class.java
            ),
            list,
            { union(listOf(2, 4)) },
            { union(listOf(2, 4)).toMutableList() }
        )
    }

    /* ============================================================
       Duplicate Behavior
       ============================================================ */

    @Test
    fun duplicate_handling_parity() {
        val list = listOf(1, 1, 2, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "intersect",
                Iterable::class.java
            ),
            list,
            { intersect(listOf(1, 2)) },
            { intersect(listOf(1, 2)).toMutableList() }
        )
    }

    /* ============================================================
       Null Handling
       ============================================================ */

    @Test
    fun null_handling_parity() {
        val list = listOf("a", null, "b")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "minus",
                Any::class.java
            ),
            list,
            { minus(null) },
            { minus(null as? String?).toMutableList() }
        )
    }

    /* ============================================================
       Empty List Behavior
       ============================================================ */

    @Test
    fun empty_set_operations_parity() {
        val empty = emptyList<Int>()

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "union",
                Iterable::class.java
            ),
            empty,
            { union(listOf(1)) },
            { union(listOf(1)).toMutableList() }
        )

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "minus",
                Any::class.java
            ),
            empty,
            { minus(1) },
            { minus(1).toMutableList() }
        )
    }

    /* ============================================================
       Randomized Fuzz Stress
       ============================================================ */

    @Test
    fun randomized_set_algebra_stress() {
        repeat(1000) {
            val base = List(10) { (-5..5).random() }
            val other = List(5) { (-5..5).random() }

            ParityHarness.compare(
                MethodSignature.fromNameAndParams(
                    "union",
                    Iterable::class.java
                ),
                base,
                { union(other) },
                { union(other).toMutableList() }
            )

            ParityHarness.compare(
                MethodSignature.fromNameAndParams(
                    "intersect",
                    Iterable::class.java
                ),
                base,
                { intersect(other) },
                { intersect(other).toMutableList() }
            )

            ParityHarness.compare(
                MethodSignature.fromNameAndParams(
                    "minus",
                    Iterable::class.java
                ),
                base,
                { minus(other) },
                { minus(other).toMutableList() }
            )
        }
    }
}
