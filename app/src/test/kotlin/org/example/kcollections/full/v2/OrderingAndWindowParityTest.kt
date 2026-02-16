package org.example.kcollections.full.v2


import org.junit.jupiter.api.Test
import java.util.Comparator
import java.util.function.Function

class OrderingAndWindowParityTest {

    /* ============================================================
       sorted()
       ============================================================ */

    @Test
    fun sorted_parity() {
        val list = listOf(3, 1, 2)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams("sorted"),
            list,
            { sorted() },
            { sorted().toMutableList() }
        )
    }

    /* ============================================================
       sortedDescending()
       ============================================================ */

    @Test
    fun sortedDescending_parity() {
        val list = listOf(3, 1, 2)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams("sortedDescending"),
            list,
            { sortedDescending() },
            { sortedDescending().toMutableList() }
        )
    }

    /* ============================================================
       sortedBy(Function)
       ============================================================ */

    @Test
    fun sortedBy_parity() {
        val list = listOf("aaa", "b", "cc")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "sortedBy",
                Function::class.java
            ),
            list,
            { sortedBy { it.length } },
            { sortedBy { it.length }.toMutableList() }
        )
    }

    /* ============================================================
       reversed()
       ============================================================ */

    @Test
    fun reversed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams("reversed"),
            list,
            { reversed() },
            { reversed().toMutableList() }
        )
    }

    /* ============================================================
       asReversedView()
       ============================================================ */

    @Test
    fun asReversedView_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams("asReversedView"),
            list,
            { reversed() }, // Kotlin equivalent
            { asReversedView().toMutableList() }
        )
    }

    /* ============================================================
       chunked(int)
       ============================================================ */

    @Test
    fun chunked_parity() {
        val list = listOf(1, 2, 3, 4, 5)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "chunked",
                Int::class.javaPrimitiveType!!
            ),
            list,
            { chunked(2) },
            { chunked(2).toMutableList() }
        )
    }

    @Test
    fun chunked_invalid_size_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "chunked",
                Int::class.javaPrimitiveType!!
            ),
            list,
            { chunked(0) },
            { chunked(0) }
        )
    }

    /* ============================================================
       windowed(int, int, boolean)
       ============================================================ */

    @Test
    fun windowed_partial_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "windowed",
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!,
                Boolean::class.javaPrimitiveType!!
            ),
            list,
            { windowed(3, 1, true) },
            { windowed(3, 1, true).toMutableList() }
        )
    }

    @Test
    fun windowed_no_partial_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "windowed",
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!,
                Boolean::class.javaPrimitiveType!!
            ),
            list,
            { windowed(3, 1, false) },
            { windowed(3, 1, false).toMutableList() }
        )
    }

    @Test
    fun windowed_invalid_args_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "windowed",
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!,
                Boolean::class.javaPrimitiveType!!
            ),
            list,
            { windowed(0, 1, true) },
            { windowed(0, 1, true) }
        )
    }

    /* ============================================================
       zip(Iterable)
       ============================================================ */

    @Test
    fun zip_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "zip",
                Iterable::class.java
            ),
            list,
            { zip(listOf("a", "b")) },
            { zip(listOf("a", "b")).toMutableList() }
        )
    }

    /* ============================================================
       Edge Cases
       ============================================================ */

    @Test
    fun empty_ordering_parity() {
        val empty = emptyList<Int>()

        ParityHarness.compare(
            MethodSignature.fromNameAndParams("sorted"),
            empty,
            { sorted() },
            { sorted().toMutableList() }
        )
    }

    @Test
    fun single_element_window_parity() {
        val list = listOf(42)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "windowed",
                Int::class.javaPrimitiveType!!,
                Int::class.javaPrimitiveType!!,
                Boolean::class.javaPrimitiveType!!
            ),
            list,
            { windowed(2, 1, true) },
            { windowed(2, 1, true).toMutableList() }
        )
    }
}
