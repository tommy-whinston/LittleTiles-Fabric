package team.creative.littletiles.mixin.client;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;

@Mixin(EntityRenderers.class)
public interface EntityRenderersAccessor {

    @Accessor("PROVIDERS")
    static Map<EntityType<?>, EntityRendererProvider<?>> getProviders() {
        throw new UnsupportedOperationException();
    }
}
