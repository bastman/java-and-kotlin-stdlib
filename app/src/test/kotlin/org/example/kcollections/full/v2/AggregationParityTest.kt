package org.example.kcollections.full.v2

import org.junit.jupiter.api.Test
import java.util.function.*
import kotlin.random.Random

class AggregationParityTest {

    /* ============================================================
       fold / foldIndexed
       ============================================================ */

    @Test
    fun fold_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "fold",
                Any::class.java,
                BiFunction::class.java
            ),
            list,
            { fold(0) { acc, value -> acc + value } },
            { fold(0) { acc, value -> acc + value } }
        )
    }

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
            { foldIndexed(0) { index, acc, value -> acc + index + value } },
            { foldIndexed(0) { index, acc, value -> acc + index + value } }
        )
    }

    /* ============================================================
       runningFold / runningFoldIndexed
       ============================================================ */

    @Test
    fun runningFold_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "runningFold",
                Any::class.java,
                BiFunction::class.java
            ),
            list,
            { runningFold(0) { acc, value -> acc + value } },
            { runningFold(0) { acc, value -> acc + value }.toMutableList() }
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
            { runningFoldIndexed(0) { index, acc, value -> acc + index + value } },
            { runningFoldIndexed(0) { index, acc, value -> acc + index + value }.toMutableList() }
        )
    }

    /* ============================================================
       reduce family
       ============================================================ */

    @Test
    fun reduce_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "reduce",
                BiFunction::class.java
            ),
            list,
            { reduce(Int::plus) },
            { reduce(Integer::sum) }
        )
    }

    @Test
    fun reduce_empty_parity() {
        val list = emptyList<Int>()

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "reduce",
                BiFunction::class.java
            ),
            list,
            { reduce(Int::plus) },
            { reduce(Integer::sum) }
        )
    }

    @Test
    fun reduceIndexed_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "reduceIndexed",
                IndexedBiFunction::class.java
            ),
            list,
            { reduceIndexed { index, acc, value -> acc + index + value } },
            { reduceIndexed { index, acc, value -> acc + index + value } }
        )
    }

    @Test
    fun reduceOrNull_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "reduceOrNull",
                BiFunction::class.java
            ),
            list,
            { reduceOrNull(Int::plus) },
            { reduceOrNull(Integer::sum) }
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
            { reduceIndexedOrNull { index, acc, value -> acc + index + value } },
            { reduceIndexedOrNull { index, acc, value -> acc + index + value } }
        )
    }

    /* ============================================================
       runningReduce
       ============================================================ */

    @Test
    fun runningReduce_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "runningReduce",
                BiFunction::class.java
            ),
            list,
            { runningReduce { acc, value -> acc + value } },
            { runningReduce { acc, value -> acc + value }.toMutableList() }
        )
    }

    /* ============================================================
       sumOf family
       ============================================================ */

    @Test
    fun sumOfInt_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "sumOfInt",
                ToIntFunction::class.java
            ),
            list,
            { sumOf { it } },
            { sumOfInt { it } }
        )
    }

    @Test
    fun sumOfLong_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "sumOfLong",
                ToLongFunction::class.java
            ),
            list,
            { sumOf { it.toLong() } },
            { sumOfLong { it.toLong() } }
        )
    }

    @Test
    fun sumOfDouble_parity() {
        val list = listOf(1, 2, 3)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "sumOfDouble",
                ToDoubleFunction::class.java
            ),
            list,
            { sumOf { it.toDouble() } },
            { sumOfDouble { it.toDouble() } }
        )
    }

    /* ============================================================
       min/max families
       ============================================================ */

    @Test
    fun maxBy_parity() {
        val list = listOf(5, 1, 10)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "maxBy",
                Function::class.java
            ),
            list,
            { maxByOrNull { it } },
            { maxByOrNull { it } }
        )
    }

    @Test
    fun minBy_parity() {
        val list = listOf(5, 1, 10)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "minBy",
                Function::class.java
            ),
            list,
            { minByOrNull { it } },
            { minByOrNull { it } }
        )
    }

    @Test
    fun maxOf_parity() {
        val list = listOf(5, 1, 10)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "maxOf",
                Function::class.java
            ),
            list,
            { maxOf { it } },
            { maxOf { it } }
        )
    }

    @Test
    fun minOf_parity() {
        val list = listOf(5, 1, 10)

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "minOf",
                Function::class.java
            ),
            list,
            { minOf { it } },
            { minOf { it } }
        )
    }

    /* ============================================================
       Fuzz Aggregation Stress
       ============================================================ */

    @Test
    fun aggregation_randomized_stress() {
        repeat(500) {
            val size = Random.nextInt(0, 20)
            val list = List(size) { Random.nextInt(-10, 10) }

            ParityHarness.compare(
                MethodSignature.fromNameAndParams(
                    "sumOfInt",
                    ToIntFunction::class.java
                ),
                list,
                { sumOf { it } },
                { sumOfInt { it } }
            )
        }
    }
}
