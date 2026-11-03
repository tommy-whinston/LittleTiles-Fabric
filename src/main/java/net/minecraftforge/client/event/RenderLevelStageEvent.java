package net.minecraftforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraftforge.eventbus.api.Event;
import org.joml.Matrix4f;

public class RenderLevelStageEvent extends Event {

    public enum Stage {
        AFTER_SKY,
        AFTER_SOLID_BLOCKS,
        AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS,
        AFTER_CUTOUT_BLOCKS,
        AFTER_ENTITIES,
        AFTER_TRANSLUCENT_BLOCKS,
        AFTER_TRIPWIRE_BLOCKS,
        AFTER_WEATHER,
        AFTER_LEVEL
    }

    private final Stage stage;
    private final PoseStack poseStack;
    private final float partialTick;
    private final Camera camera;
    private final Frustum frustum;
    private final Matrix4f projectionMatrix;

    public RenderLevelStageEvent() {
        this.stage = null;
        this.poseStack = null;
        this.partialTick = 0;
        this.camera = null;
        this.frustum = null;
        this.projectionMatrix = null;
    }

    public RenderLevelStageEvent(Stage stage, PoseStack poseStack, float partialTick, Camera camera, Frustum frustum, Matrix4f projectionMatrix) {
        this.stage = stage;
        this.poseStack = poseStack;
        this.partialTick = partialTick;
        this.camera = camera;
        this.frustum = frustum;
        this.projectionMatrix = projectionMatrix;
    }

    public Stage getStage() {
        return stage;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public Camera getCamera() {
        return camera;
    }

    public Frustum getFrustum() {
        return frustum;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
