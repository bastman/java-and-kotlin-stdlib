package org.example.kcollections.full.v1;

public record Pair<A, B>(A first, B second) {

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }

    public <R> Pair<R, B> mapFirst(java.util.function.Function<? super A, ? extends R> mapper) {
        return new Pair<>(mapper.apply(first), second);
    }

    public <R> Pair<A, R> mapSecond(java.util.function.Function<? super B, ? extends R> mapper) {
        return new Pair<>(first, mapper.apply(second));
    }

    public void ifPresent(java.util.function.BiConsumer<? super A, ? super B> consumer) {
        consumer.accept(first, second);
    }
}
