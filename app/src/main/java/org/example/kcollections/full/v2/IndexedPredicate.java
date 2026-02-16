package org.example.kcollections.full.v2;

    /* ============================================================
       Indexed Functional Types (Kotlin Parity)
       ============================================================ */

@FunctionalInterface
public interface IndexedPredicate<T> {
    boolean test(int index, T element);
}
