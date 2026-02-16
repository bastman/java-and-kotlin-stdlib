package org.example.kcollections.full.v2

import org.junit.jupiter.api.Test
import java.util.Map
import java.util.function.Function

class GroupingAndAssociationParityTest {

    /* ============================================================
       associate(Function)
       ============================================================ */

    @Test
    fun associate_parity() {
        val list = listOf("a", "bb", "ccc")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associate",
                Function::class.java
            ),
            list,
            { associate { it.length to it.uppercase() } },
            { associate { Map.entry(it.length, it.uppercase()) } }
        )
    }

    /* ============================================================
       associateBy(keySelector)
       ============================================================ */

    @Test
    fun associateBy_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateBy",
                Function::class.java
            ),
            list,
            { associateBy { it.length } },
            { associateBy { it.length } }
        )
    }

    /* ============================================================
       associateBy(keySelector, valueTransform)
       ============================================================ */

    @Test
    fun associateBy_transform_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateBy",
                Function::class.java,
                Function::class.java
            ),
            list,
            { associateBy({ it.length }, { it.uppercase() }) },
            { associateBy({ it.length }, { it.uppercase() }) }
        )
    }

    /* ============================================================
       associateWith
       ============================================================ */

    @Test
    fun associateWith_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateWith",
                Function::class.java
            ),
            list,
            { associateWith { it.length } },
            { associateWith { it.length } }
        )
    }

    /* ============================================================
       associateWithIndexed
       ============================================================ */

    @Test
    fun associateWithIndexed_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateWithIndexed",
                IndexedFunction::class.java
            ),
            list,
            { mapIndexed { index, value -> value to (index + value.length) }.toMap() },
            { associateWithIndexed { index, value -> index + value.length } }
        )
    }

    /* ============================================================
       groupBy(keySelector)
       ============================================================ */

    @Test
    fun groupBy_parity() {
        val list = listOf("a", "bb", "c", "dd")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "groupBy",
                Function::class.java
            ),
            list,
            { groupBy { it.length } },
            { groupBy { it.length } }
        )
    }

    /* ============================================================
       groupBy(keySelector, valueTransform)
       ============================================================ */

    @Test
    fun groupBy_valueTransform_parity() {
        val list = listOf("a", "bb", "c", "dd")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "groupBy",
                Function::class.java,
                Function::class.java
            ),
            list,
            { groupBy({ it.length }, { it.uppercase() }) },
            { groupBy({ it.length }, { it.uppercase() }) }
        )
    }

    /* ============================================================
       Duplicate Keys (Last Wins)
       ============================================================ */

    @Test
    fun duplicate_keys_last_wins_parity() {
        val list = listOf("a", "b")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateBy",
                Function::class.java
            ),
            list,
            { associateBy { 1 } },
            { associateBy { 1 } }
        )
    }

    /* ============================================================
       Null Keys and Values
       ============================================================ */

    @Test
    fun null_key_parity() {
        val list = listOf("a", null, "bb")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateBy",
                Function::class.java
            ),
            list,
            { associateBy { it?.length } },
            { associateBy { it?.length } }
        )
    }

    @Test
    fun null_value_parity() {
        val list = listOf("a", "bb", "c")

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "associateBy",
                Function::class.java,
                Function::class.java
            ),
            list,
            { associateBy({ it.length }, { null }) },
            { associateBy({ it.length }, { null }) }
        )
    }

    /* ============================================================
       Empty List Behavior
       ============================================================ */

    @Test
    fun empty_list_grouping_parity() {
        val empty = emptyList<String>()

        ParityHarness.compare(
            MethodSignature.fromNameAndParams(
                "groupBy",
                Function::class.java
            ),
            empty,
            { groupBy { it.length } },
            { groupBy { it.length } }
        )
    }
}
