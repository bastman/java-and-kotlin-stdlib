package org.example.kcollections.full.v2;

@FunctionalInterface
public interface IndexedFunction<T, R> {
    R apply(int index, T element);
}
