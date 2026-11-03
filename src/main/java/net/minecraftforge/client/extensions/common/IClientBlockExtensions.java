package net.minecraftforge.client.extensions.common;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IClientBlockExtensions {

    IClientBlockExtensions DEFAULT = new IClientBlockExtensions() {
    };

    static IClientBlockExtensions of(BlockState state) {
        return DEFAULT;
    }

    default boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
        return false;
    }

    default boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
        return false;
    }

    default int getFogColor(BlockState state, Level level, BlockPos pos, Entity entity, int originalColor, float partialTicks) {
        return originalColor;
    }

    default boolean shouldRenderOffScreen(BlockState state) {
        return false;
    }

    default Vec3 getFogColor(BlockState state, Level level, BlockPos pos, Entity entity, Vec3 originalColor, float partialTicks) {
        return originalColor;
    }

    default Vector3d getFogColor(BlockState state, Level level, BlockPos pos, Entity entity, Vector3d originalColor, float partialTicks) {
        return originalColor;
    }
}
