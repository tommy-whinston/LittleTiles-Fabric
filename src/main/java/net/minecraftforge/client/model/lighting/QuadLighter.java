package net.minecraftforge.client.model.lighting;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Minimal Fabric-side replacement for Forge's quad lighter.
 * It uses the vanilla packed light of the block position and writes the
 * already-cropped quads into the chunk buffer. */
public class QuadLighter {

    protected int light = 0;

    public void setup(Level level, BlockPos pos, BlockState state) {
        light = LevelRenderer.getLightColor(level, pos);
    }

    public void process(BufferBuilder builder, PoseStack.Pose pose, BakedQuad quad, int overlay) {
        builder.putBulkData(pose, quad, 1.0F, 1.0F, 1.0F, light, overlay);
    }

    public void reset() {
    }
}
