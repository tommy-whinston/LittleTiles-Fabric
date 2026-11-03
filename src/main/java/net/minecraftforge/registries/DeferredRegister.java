package net.minecraftforge.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class DeferredRegister<T> {

    private final Registry<T> registry;
    private final String modid;
    private final List<RegistryObject<T>> entries = new ArrayList<>();

    private DeferredRegister(Registry<T> registry, String modid) {
        this.registry = registry;
        this.modid = modid;
    }

    public static <T> DeferredRegister<T> create(Registry<T> registry, String modid) {
        return new DeferredRegister<>(registry, modid);
    }

    public static <T> DeferredRegister<T> create(IForgeRegistry<T> registry, String modid) {
        return new DeferredRegister<>(registry.getRegistry(), modid);
    }

    public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> object = new RegistryObject<>(new ResourceLocation(modid, name), supplier);
        entries.add((RegistryObject<T>) object);
        return object;
    }

    public void register(IEventBus bus) {
        for (RegistryObject<T> object : entries) {
            T value = object.get();
            Registry.register(registry, object.getId(), value);
            object.setValue(value);
        }
    }
}
