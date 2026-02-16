package org.example.kcollections.full.v2


import org.junit.jupiter.api.Test
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.BiFunction

class IndexedParityTest {

    /* ============================================================
       mapIndexed
       ============================================================ */

    @Test
    fun mapIndexed_parity() {
        val list = listOf(10, 20, 30)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "mapIndexed",
                IndexedFunction::class.java
            ),
            list,
            { mapIndexed { index, value -> index + value } },
            { mapIndexed { index, value -> index + value }.toMutableList() }
        )
    }

    /* ============================================================
       mapIndexedNotNull
       ============================================================ */

    @Test
    fun mapIndexedNotNull_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "mapIndexedNotNull",
                IndexedFunction::class.java
            ),
            list,
            { mapIndexedNotNull { index, value -> if (index % 2 == 0) value else null } },
            { mapIndexedNotNull { index, value -> if (index % 2 == 0) value else null }
                .toMutableList() }
        )
    }

    /* ============================================================
       filterIndexed
       ============================================================ */

    @Test
    fun filterIndexed_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "filterIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { filterIndexed { index, _ -> index % 2 == 0 } },
            { filterIndexed { index, _ -> index % 2 == 0 }.toMutableList() }
        )
    }

    /* ============================================================
       filterNotIndexed
       ============================================================ */

    @Test
    fun filterNotIndexed_parity() {
        val list = listOf(1, 2, 3, 4)

        val kt = list.filterNot {
            index ->
            val r=index % 2 == 0
            r
        }

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "filterNotIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { filterNot { index -> index % 2 == 0 } }, // Kotlin workaround
            { filterNotIndexed { index, _ -> index % 2 == 0 }.toMutableList() }
        )
    }

    /* ============================================================
       Predicate Indexed Variants
       ============================================================ */

    @Test
    fun allIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "allIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { indices.all { get(it) > 0 } },
            { allIndexed { _, v -> v > 0 } }
        )
    }

    @Test
    fun anyIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "anyIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { indices.any { get(it) == 2 } },
            { anyIndexed { _, v -> v == 2 } }
        )
    }

    @Test
    fun noneIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "noneIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { indices.none { get(it) == 99 } },
            { noneIndexed { _, v -> v == 99 } }
        )
    }

    @Test
    fun countIndexed_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "countIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { indices.count { get(it) % 2 == 0 } },
            { countIndexed { _, v -> v % 2 == 0 } }
        )
    }

    /* ============================================================
       takeWhileIndexed / dropWhileIndexed
       ============================================================ */

    @Test
    fun takeWhileIndexed_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "takeWhileIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { takeWhile { it < 3 } }, // Kotlin equivalent
            { takeWhileIndexed { _, v -> v < 3 }.toMutableList() }
        )
    }

    @Test
    fun dropWhileIndexed_parity() {
        val list = listOf(1, 2, 3, 4)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "dropWhileIndexed",
                IndexedPredicate::class.java
            ),
            list,
            { dropWhile { it < 3 } },
            { dropWhileIndexed { _, v -> v < 3 }.toMutableList() }
        )
    }

    /* ============================================================
       foldIndexed / runningFoldIndexed
       ============================================================ */

    @Test
    fun foldIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "foldIndexed",
                Any::class.java,
                IndexedBiFunction::class.java
            ),
            list,
            { foldIndexed(0) { index, acc, v -> acc + index + v } },
            { foldIndexed(0) { index, acc, v -> acc + index + v } }
        )
    }

    @Test
    fun runningFoldIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "runningFoldIndexed",
                Any::class.java,
                IndexedBiFunction::class.java
            ),
            list,
            { runningFoldIndexed(0) { index, acc, v -> acc + index + v } },
            { runningFoldIndexed(0) { index, acc, v -> acc + index + v }
                .toMutableList() }
        )
    }

    /* ============================================================
       reduceIndexed / reduceIndexedOrNull
       ============================================================ */

    @Test
    fun reduceIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "reduceIndexed",
                IndexedBiFunction::class.java
            ),
            list,
            { reduceIndexed { index, acc, v -> acc + index + v } },
            { reduceIndexed { index, acc, v -> acc + index + v } }
        )
    }

    @Test
    fun reduceIndexedOrNull_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "reduceIndexedOrNull",
                IndexedBiFunction::class.java
            ),
            list,
            { reduceIndexedOrNull { index, acc, v -> acc + index + v } },
            { reduceIndexedOrNull { index, acc, v -> acc + index + v } }
        )
    }
}

