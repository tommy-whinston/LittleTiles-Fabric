package team.creative.littletiles.mixin.client.render;

import java.nio.ByteBuffer;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.RenderSystem.AutoStorageIndexBuffer;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.IndexType;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import team.creative.littletiles.client.render.mc.VertexBufferExtender;

@Mixin(VertexBuffer.class)
public abstract class VertexBufferMixin implements VertexBufferExtender {
    
    @Shadow
    private int vertexBufferId;
    @Nullable
    @Shadow
    private VertexFormat format;
    @Nullable
    @Shadow
    private RenderSystem.AutoStorageIndexBuffer sequentialIndices;
    @Shadow
    private VertexFormat.IndexType indexType;
    @Shadow
    private int indexCount;
    @Shadow
    private VertexFormat.Mode mode;
    @Unique
    public int length;
    
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V"),
            method = "uploadVertexBuffer(Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;Ljava/nio/ByteBuffer;)Lcom/mojang/blaze3d/vertex/VertexFormat;", require = 1)
    public void uploadVertexBuffer(BufferBuilder.DrawState drawState, ByteBuffer buffer, CallbackInfoReturnable<VertexFormat> info) {
        length = drawState.vertexBufferSize();
    }
    
    public int getLastUploadedLength() {
        return length;
    }
    
    public int getVertexBufferId() {
        return vertexBufferId;
    }
    
    public void setFormat(VertexFormat format) {
        this.format = format;
    }
    
    public int getIndexCount() {
        return indexCount;
    }
    
    public void setIndexCount(int count) {
        this.indexCount = count;
    }
    
    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
    }
    
    public void setLastUploadedLength(int length) {
        this.length = length;
    }
    
    public Mode getMode() {
        return mode;
    }
    
    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
    public void setSequentialIndices(AutoStorageIndexBuffer indexBuffer) {
        this.sequentialIndices = indexBuffer;
    }
    
    public RenderSystem.AutoStorageIndexBuffer getSequentialIndices() {
        return sequentialIndices;
    }
    
}
