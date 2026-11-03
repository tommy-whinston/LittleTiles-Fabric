package net.minecraftforge.registries;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class RegistryObject<T> implements Supplier<T> {

    private final ResourceLocation id;
    private final Supplier<? extends T> supplier;
    private T value;

    RegistryObject(ResourceLocation id, Supplier<? extends T> supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (value == null)
            value = supplier.get();
        return value;
    }

    public ResourceLocation getId() {
        return id;
    }

    public boolean isPresent() {
        return value != null || supplier != null;
    }

    void setValue(T value) {
        this.value = value;
    }

    public Supplier<? extends T> getSupplier() {
        return supplier;
    }
}
