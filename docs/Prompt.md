# Prompt: kotlin List to java

I want to implement a single Java class KList<T> that achieves strict behavioral parity
with Kotlin's List<T> + kotlin.collections extension API.

Requirements:

1. Strict Kotlin behavioral parity:
    - Same method names
    - Same overload structure
    - Same exception types and semantics
    - Same null handling
    - Same order guarantees
    - Last-wins semantics where Kotlin does

2. Java 17+ allowed.

3. No use of Streams.
   All implementations must be loop-based and eager.

4. Exclude Sequence (no lazy evaluation).

5. Include:
    - Full element retrieval family
    - Full predicate family
    - Filtering + indexed variants
    - Mapping + indexed variants
    - Aggregation (reduce, fold, running variants)
    - Numeric sumOf overloads
    - Association + grouping
    - Zip + windowed + chunked
    - Ordering family
    - Distinct + distinctBy
    - Set algebra (union, intersect, subtract)
    - plus/minus operators

6. Deliver in structured phases and preserve clean code organization.

Start with Phase 1 and proceed systematically.


Do not simplify semantics.
Do not collapse overloads.
Do not rename methods.
Use LinkedHashMap where Kotlin preserves order.
Throw the same exception types Kotlin does.
Follow Kotlin stdlib grouping structure.


# If You Want Production-Grade Output

Optimize for RandomAccess lists.
Pre-size ArrayList when possible.
Minimize allocations.
Keep behavior consistent with Kotlin stdlib.


