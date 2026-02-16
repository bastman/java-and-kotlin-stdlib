package org.example.kcollections.full.v2;

@FunctionalInterface
public interface IndexedBiFunction<A, B, R> {
    R apply(int index, A a, B b);
}
