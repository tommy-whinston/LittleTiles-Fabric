package team.creative.littletiles.client.compat;

import java.util.Map;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import team.creative.littletiles.mixin.client.EntityRenderersAccessor;

public final class EntityRendererHelper {

    private EntityRendererHelper() {
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> provider) {
        ((Map) EntityRenderersAccessor.getProviders()).put(type, provider);
    }
}
