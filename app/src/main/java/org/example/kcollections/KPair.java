package org.example.kcollections;

public record KPair<A, B>(A first, B second) {

    public static <A, B> KPair<A, B> of(A first, B second) {
        return new KPair<>(first, second);
    }

    public <R> KPair<R, B> mapFirst(java.util.function.Function<? super A, ? extends R> mapper) {
        return new KPair<>(mapper.apply(first), second);
    }

    public <R> KPair<A, R> mapSecond(java.util.function.Function<? super B, ? extends R> mapper) {
        return new KPair<>(first, mapper.apply(second));
    }

    public void ifPresent(java.util.function.BiConsumer<? super A, ? super B> consumer) {
        consumer.accept(first, second);
    }
}
