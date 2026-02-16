package org.example.kcollections.full.v2


import org.junit.jupiter.api.Test
import java.util.Comparator
import java.util.function.Function

class BinarySearchAdvancedParityTest {

    /* ============================================================
       Simple Comparable Variant
       ============================================================ */

    @Test
    fun binarySearch_simple_parity() {
        val list = listOf(1, 3, 5, 7)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java
            ),
            list,
            { binarySearch(5) },
            { binarySearch(5) }
        )

        // insertion point case
        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java
            ),
            list,
            { binarySearch(4) },
            { binarySearch(4) }
        )
    }

    /* ============================================================
       Range Variant
       ============================================================ */

    @Test
    fun binarySearch_range_parity() {
        val list = listOf(1, 3, 5, 7)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java,
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!
            ),
            list,
            { binarySearch(5, 1, 4) },
            { binarySearch(5, 1, 4) }
        )

        // insertion inside range
        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java,
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!
            ),
            list,
            { binarySearch(6, 1, 4) },
            { binarySearch(6, 1, 4) }
        )
    }

    @Test
    fun binarySearch_range_exception_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java,
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!
            ),
            list,
            { binarySearch(2, -1, 2) },
            { binarySearch(2, -1, 2) }
        )
    }

    /* ============================================================
       Comparator Variant
       ============================================================ */

    @Test
    fun binarySearch_comparator_parity() {
        val list = listOf("a", "bb", "ccc")

        val comparator = Comparator<String> { a, b ->
            a.length.compareTo(b.length)
        }

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java,
                Comparator::class.java
            ),
            list,
            { binarySearch("bb", comparator) },
            { binarySearch("bb", comparator) }
        )

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java,
                Comparator::class.java
            ),
            list,
            { binarySearch("zzzz", comparator) },
            { binarySearch("zzzz", comparator) }
        )
    }

    @Test
    fun binarySearch_comparator_range_parity() {
        val list = listOf("a", "bb", "ccc")

        val comparator = Comparator<String> { a, b ->
            a.length.compareTo(b.length)
        }

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java,
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!,
                Comparator::class.java
            ),
            list,
            { binarySearch("bb", comparator,0, size, ) },
            { binarySearch("bb", comparator,0, size, ) }
        )
    }

    /* ============================================================
       binarySearchBy
       ============================================================ */

    @Test
    fun binarySearchBy_simple_parity() {
        val list = listOf("a", "bb", "ccc")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearchBy",
                Comparable::class.java,
                Function::class.java
            ),
            list,
            { binarySearchBy(2) { it.length } },
            { binarySearchBy(2) { it.length } }
        )

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearchBy",
                Comparable::class.java,
                Function::class.java
            ),
            list,
            { binarySearchBy(4) { it.length } },
            { binarySearchBy(4) { it.length } }
        )
    }

    @Test
    fun binarySearchBy_range_parity() {
        val list = listOf("a", "bb", "ccc", "dddd")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearchBy",
                Comparable::class.java,
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!,
                Function::class.java
            ),
            list,
            { binarySearchBy(3, 0, size) { it.length } },
            { binarySearchBy(3, 0, size) { it.length } }
        )
    }

    /* ============================================================
       Empty List Behavior
       ============================================================ */

    @Test
    fun binarySearch_empty_list_parity() {
        val empty = emptyList<Int>()

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "binarySearch",
                Any::class.java
            ),
            empty,
            { binarySearch(1) },
            { binarySearch(1) }
        )
    }

    /* ============================================================
       Randomized Stress (Sorted Inputs)
       ============================================================ */

    @Test
    fun binarySearch_randomized_stress() {
        repeat(1000) {
            val list = (-10..10).shuffled().take(10).sorted()
            val target = (-15..15).random()

            ParityHarness.compare(
                MethodSignature.fromNameAndParams(
                    "binarySearch",
                    Any::class.java
                ),
                list,
                { binarySearch(target) },
                { binarySearch(target) }
            )
        }
    }
}
