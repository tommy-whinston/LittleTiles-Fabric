package net.minecraftforge.registries;

import java.util.Set;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

/**
 * Small adapter that exposes the subset of Forge's IForgeRegistry used by LittleTiles.
 */
public class IForgeRegistry<T> {

    private final Registry<T> registry;

    public IForgeRegistry(Registry<T> registry) {
        this.registry = registry;
    }

    public Registry<T> getRegistry() {
        return registry;
    }

    public T getValue(ResourceLocation id) {
        return registry.get(id);
    }

    public ResourceLocation getKey(T value) {
        return registry.getKey(value);
    }

    public boolean containsKey(ResourceLocation id) {
        return registry.containsKey(id);
    }

    public Set<ResourceLocation> keySet() {
        return registry.keySet();
    }

    public <I extends T> I register(ResourceLocation id, I value) {
        return Registry.register(registry, id, value);
    }
}
