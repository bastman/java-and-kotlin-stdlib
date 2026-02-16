# 🔥 STRICT KOTLIN ↔ KLIST PARITY TEST SUITE GENERATOR PROMPT

You are generating a **production-grade Kotlin ↔ Java behavioral parity
test suite** for a Java class:

    kcollections.KList<T>

This class is intended to have **strict behavioral parity** with:

    kotlin.collections.List<T>
    + kotlin.collections extension API

------------------------------------------------------------------------

# 🎯 OBJECTIVE

Generate a complete parity testing framework that:

1.  Compares Kotlin stdlib behavior vs Java `KList`
2.  Validates:
    -   Return values
    -   Collection structure equality
    -   Map insertion order
    -   Duplicate semantics
    -   Null handling
    -   Exception type parity
    -   Binary search negative insertion encoding
3.  Enforces strict overload-level API coverage
4.  Fails the build if any public overload is not tested
5.  Uses JUnit 5 only (no custom assertion engine)
6.  Uses Kotlin test sources (`src/test/kotlin`)
7.  Is CI-ready
8.  Includes deterministic tests + randomized fuzz tests

------------------------------------------------------------------------

# 🧱 ARCHITECTURE REQUIREMENTS

Generate the following files:

    parity/
     ├── JavaAdapter.kt
     ├── MethodSignature.kt
     ├── ParityRegistry.kt
     ├── ParityHarness.kt
     ├── KListApiCoverageTest.kt
     ├── KListStrictParityTest.kt
     ├── AggregationParityTest.kt
     ├── BinarySearchAdvancedParityTest.kt
     ├── GroupingAndAssociationParityTest.kt
     ├── IndexedParityTest.kt
     ├── OrderingAndWindowParityTest.kt
     ├── SetAlgebraParityTest.kt

All files must be complete and production-ready.

------------------------------------------------------------------------

# 🔒 STRICT ENFORCEMENT REQUIREMENTS

The suite MUST:

-   Register exact method signatures
-   Distinguish overloads
-   Use reflection to fail build if any public KList overload is
    uncovered
-   Exclude synthetic and bridge methods
-   Use `declaredMethods`, not inherited interface methods
-   Normalize signatures as:

```{=html}
<!-- -->
```
    methodName(paramType1,paramType2)

Example:

    binarySearch(java.lang.Object,int,int)
    map(java.util.function.Function)

------------------------------------------------------------------------

# 🧪 PARITY HARNESS REQUIREMENTS

`ParityHarness` must:

-   Accept exact signature string
-   Register coverage in `ParityRegistry`
-   Execute Kotlin block
-   Execute Java `KList` block
-   Capture exceptions using `runCatching`
-   Compare:
    -   If both fail → compare exception class
    -   If both succeed → compare values using `assertEquals`
-   Use JUnit 5 Assertions only
-   No custom deep-equals implementation
-   No reflection invocation of Kotlin stdlib

------------------------------------------------------------------------

# 📦 COVERAGE ENFORCEMENT

`KListApiCoverageTest` must:

-   Reflect over all public declared methods in `KList`
-   Ignore synthetic & bridge methods
-   Generate full normalized signature string
-   Compare against `ParityRegistry.coveredMethods()`
-   Fail test if any uncovered overload exists
-   Print missing signatures clearly

------------------------------------------------------------------------

# 🧪 TEST COVERAGE REQUIREMENTS

The parity suite must cover ALL public methods of KList including:

-   Retrieval family
-   Predicate family
-   Filtering family
-   Mapping family
-   Association & Grouping
-   Aggregation family
-   Distinct & Set algebra
-   Ordering family
-   Structural operations
-   Binary search family
-   Utility methods

------------------------------------------------------------------------

# 🎲 RANDOMIZED STRESS TESTS

Include fuzz tests (minimum 1000 iterations each) for:

-   map
-   filter
-   distinct
-   union/intersect/minus
-   binarySearch (sorted inputs only)
-   grouping
-   windowed

------------------------------------------------------------------------

# 🧨 REQUIRED EDGE CASES

The test suite MUST explicitly test:

-   Empty list
-   Single element list
-   All duplicates
-   Null elements
-   Duplicate keys in associate (last wins)
-   Negative insertion point binarySearch encoding
-   Range validation exceptions
-   Invalid window sizes
-   Invalid chunk sizes
-   Short-circuit predicate behavior
-   RunningFold size correctness
-   First-win distinct semantics
-   Last-win associate semantics
-   Order preservation in LinkedHashMap results

------------------------------------------------------------------------

# 🚫 CONSTRAINTS

-   No custom assertion engine
-   No reflection-based invocation of Kotlin stdlib
-   No simplification of semantics
-   No collapsing overloads
-   No skipping overload registration
-   Must compile in Kotlin test sources
-   Must use JUnit 5 only
-   Must be production-ready
-   Must be CI-safe

------------------------------------------------------------------------

# 🏁 OUTPUT FORMAT REQUIREMENT

Output the full source code for all required files.

Do not omit any files. Do not leave placeholders. Do not explain
anything. Only output complete source code.

------------------------------------------------------------------------

# 🏆 END RESULT

The generated suite must provide:

-   Strict overload-level API governance
-   Kotlin ↔ Java semantic lockstep guarantee
-   CI-breaking coverage enforcement
-   Deterministic + fuzz parity validation
-   Production-grade behavioral contract safety
