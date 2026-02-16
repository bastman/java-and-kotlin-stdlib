package org.example.kcollections.full.v2

import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.lang.reflect.Modifier
class KListApiCoverageTest {

    @Test
    fun all_public_overloads_have_parity_tests() {

        val publicSignatures = KList::class.java.declaredMethods
            .filter { method ->
                Modifier.isPublic(method.modifiers) &&
                        !method.isSynthetic &&
                        !method.isBridge
            }
            .map { MethodSignature.fromMethod(it) }
            .toSet()

        val covered = ParityRegistry.coveredMethods()

        val uncovered = publicSignatures - covered

        if (uncovered.isNotEmpty()) {
            fail<Unit>(
                """
                The following KList public method overloads
                do NOT have parity coverage:
                
                ${uncovered.sorted().joinToString("\n")}
                
                Register them in tests using:
                
                ParityHarness.compare(
                    MethodSignature.fromNameAndParams(...),
                    ...
                )
                """.trimIndent()
            )
        }
    }
}
