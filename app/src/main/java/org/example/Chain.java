package org.example;

public final class Chain<T> {
    private final T value;

    private Chain(T value) {
        this.value = value;
    }

    public static <T> Chain<T> of(T value) {
        return new Chain<>(value);
    }

    public <R> Chain<R> map(java.util.function.Function<T, R> fn) {
        return new Chain<>(fn.apply(value));
    }

    public Chain<T> peek(java.util.function.Consumer<T> fn) {
        fn.accept(value);
        return this;
    }

    public Chain<T> filter(java.util.function.Predicate<T> predicate) {
        return predicate.test(value) ? this : new Chain<>(null);
    }

    public T get() {
        return value;
    }
}
