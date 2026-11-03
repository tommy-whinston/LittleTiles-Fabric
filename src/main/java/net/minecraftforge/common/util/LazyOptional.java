package net.minecraftforge.common.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LazyOptional<T> {

    private static final LazyOptional<?> EMPTY = new LazyOptional<>(null);

    private final Supplier<T> supplier;
    private T value;

    private LazyOptional(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> LazyOptional<T> of(Supplier<T> supplier) {
        return new LazyOptional<>(supplier);
    }

    @SuppressWarnings("unchecked")
    public static <T> LazyOptional<T> empty() {
        return (LazyOptional<T>) EMPTY;
    }

    public boolean isPresent() {
        return supplier != null;
    }

    public T orElse(T other) {
        T resolved = resolve();
        return resolved != null ? resolved : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        T resolved = resolve();
        return resolved != null ? resolved : other.get();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        T resolved = resolve();
        if (resolved != null)
            consumer.accept(resolved);
    }

    public <X extends Throwable> T orElseThrow(java.util.function.Supplier<? extends X> exceptionSupplier) throws X {
        T resolved = resolve();
        if (resolved == null)
            throw exceptionSupplier.get();
        return resolved;
    }

    public <U> LazyOptional<U> cast() {
        return (LazyOptional<U>) this;
    }

    public T resolve() {
        if (!isPresent())
            return null;
        if (value == null)
            value = supplier.get();
        return value;
    }
}
