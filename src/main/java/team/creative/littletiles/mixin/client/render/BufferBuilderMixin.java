package team.creative.littletiles.mixin.client.render;

import java.nio.ByteBuffer;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import team.creative.creativecore.mixin.BufferBuilderAccessor;
import team.creative.littletiles.client.render.cache.buffer.ChunkBufferUploader;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin implements ChunkBufferUploader {
    
    public int uploadIndex() {
        return ((BufferBuilderAccessor) this).getNextElementByte();
    }
    
    public void upload(ByteBuffer buffer) {
        if (buffer == null || !buffer.hasRemaining())
            return;
        
        BufferBuilderAccessorExt accessor = (BufferBuilderAccessorExt) this;
        int required = accessor.littletilesGetNextElementByte() + buffer.remaining();
        accessor.littletilesEnsureCapacity(required);
        
        ByteBuffer destination = accessor.littletilesGetBuffer();
        destination.position(accessor.littletilesGetNextElementByte());
        destination.put(buffer.duplicate());
        
        accessor.littletilesSetNextElementByte(required);
        int vertexSize = accessor.littletilesGetFormat().getVertexSize();
        if (vertexSize > 0)
            accessor.littletilesSetVertices(required / vertexSize);
    }
    
    public boolean hasFacingSupport() {
        return false;
    }
    
    public int uploadIndex(int facing) {
        throw new UnsupportedOperationException();
    }
    
    public void upload(int facing, ByteBuffer buffer) {
        throw new UnsupportedOperationException();
    }
    
    public void addSprite(TextureAtlasSprite texture) {}
    
    public boolean isSorted() {
        return false;
    }
}
