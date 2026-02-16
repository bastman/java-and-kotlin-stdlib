package org.example.kcollections.full.v2;


@FunctionalInterface
public interface IndexedConsumer<T> {
    void accept(int index, T element);
}
