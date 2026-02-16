package org.example.kcollections.full.v2


import org.junit.jupiter.api.Assertions.assertEquals

import java.lang.reflect.Method

object MethodSignature {

    fun fromMethod(method: Method): String {
        val params = method.parameterTypes
            .joinToString(",") { it.name }

        return "${method.name}($params)"
    }

    fun fromNameAndParams(name: String, vararg paramTypes: Class<*>): String {
        val params = paramTypes.joinToString(",") { it.name }
        return "$name($params)"
    }
}

object ParityRegistry {

    private val covered = mutableSetOf<String>()

    fun register(signature: String) {
        covered.add(signature)
    }

    fun coveredMethods(): Set<String> = covered
}

object ParityHarness {

    fun <T> compare(
        signature: String,
        source: List<T>,
        kotlinBlock: List<T>.() -> Any?,
        javaBlock: KList<T>.() -> Any?
    ) {
        // Register exact signature
        ParityRegistry.register(signature)

        val kotlinResult = runCatching { source.kotlinBlock() }
        val javaList = JavaAdapter.wrap(source)
        val javaResult = runCatching { javaList.javaBlock() }

        if (kotlinResult.isFailure || javaResult.isFailure) {
            assertEquals(
                kotlinResult.exceptionOrNull()?.javaClass,
                javaResult.exceptionOrNull()?.javaClass,
                "Exception type mismatch in $signature"
            )
        } else {
            assertEquals(
                kotlinResult.getOrNull(),
                javaResult.getOrNull(),
                "Result mismatch in $signature"
            )
        }
    }
}





object JavaAdapter {

    fun <T> wrap(list: List<T>): KList<T> {
        // defensive copy to avoid mutation side-effects
        return KList.of(list.toList())
    }
}

