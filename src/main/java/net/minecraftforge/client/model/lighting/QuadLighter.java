package net.minecraftforge.client.model.lighting;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Compile-time stand-in for Forge's quad lighter. The real lighting implementation
 * will be ported on top of the vanilla model renderer later.
 */
public class QuadLighter {

    public void setup(Level level, BlockPos pos, BlockState state) {
    }

    public void process(BufferBuilder builder, PoseStack.Pose pose, BakedQuad quad, int overlay) {
    }

    public void reset() {
    }
}
