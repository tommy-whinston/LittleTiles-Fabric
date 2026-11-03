package net.minecraftforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Event;

public class RenderHighlightEvent extends Event {

    private final HitResult target;
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final Camera camera;
    private final float partialTick;

    public RenderHighlightEvent(HitResult target, PoseStack poseStack, MultiBufferSource multiBufferSource, Camera camera, float partialTick) {
        this.target = target;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.camera = camera;
        this.partialTick = partialTick;
    }

    public HitResult getTarget() {
        return target;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public MultiBufferSource getMultiBufferSource() {
        return multiBufferSource;
    }

    public Camera getCamera() {
        return camera;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public static class Block extends RenderHighlightEvent {

        public Block(HitResult target, PoseStack poseStack, MultiBufferSource multiBufferSource, Camera camera, float partialTick) {
            super(target, poseStack, multiBufferSource, camera, partialTick);
        }
    }
}
