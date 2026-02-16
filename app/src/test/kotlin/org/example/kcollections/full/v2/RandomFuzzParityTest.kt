package org.example.kcollections.full.v2

import org.junit.jupiter.api.Test
import kotlin.random.Random

class RandomFuzzParityTest {

    @Test
    fun randomized_parity_stress() {
        repeat(2000) {
            val size = Random.Default.nextInt(0, 20)
            val list = List(size) { Random.Default.nextInt(-5, 5) }

            ParityHarness.compare("map fuzz", list,
                { map { it * 3 } },
                { map { it * 3 }.toMutableList() })

            ParityHarness.compare("filter fuzz", list,
                { filter { it % 2 == 0 } },
                { filter { it % 2 == 0 }.toMutableList() })

            ParityHarness.compare("distinct fuzz", list,
                { distinct() },
                { distinct().toMutableList() })

            ParityHarness.compare("sumOf fuzz", list,
                { sumOf { it } },
                { sumOfInt { it } })
        }
    }
}