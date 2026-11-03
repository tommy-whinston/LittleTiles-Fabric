package net.minecraftforge.client;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraft.client.renderer.RenderType;

public class ChunkRenderTypeSet implements Iterable<RenderType> {

    private final Set<RenderType> types;

    private ChunkRenderTypeSet(Set<RenderType> types) {
        this.types = types;
    }

    public static ChunkRenderTypeSet of(RenderType... types) {
        return new ChunkRenderTypeSet(new LinkedHashSet<>(Arrays.asList(types)));
    }

    public static ChunkRenderTypeSet of(Iterable<RenderType> types) {
        Set<RenderType> set = new LinkedHashSet<>();
        types.forEach(set::add);
        return new ChunkRenderTypeSet(set);
    }

    public boolean contains(RenderType type) {
        return types.contains(type);
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public Iterator<RenderType> iterator() {
        return types.iterator();
    }
}
