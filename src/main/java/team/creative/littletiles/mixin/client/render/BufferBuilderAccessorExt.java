package team.creative.littletiles.mixin.client.render;

import java.nio.ByteBuffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;

@Mixin(BufferBuilder.class)
public interface BufferBuilderAccessorExt {
    
    @Accessor("buffer")
    ByteBuffer littletilesGetBuffer();
    
    @Accessor("nextElementByte")
    int littletilesGetNextElementByte();
    
    @Accessor("nextElementByte")
    void littletilesSetNextElementByte(int value);
    
    @Accessor("vertices")
    int littletilesGetVertices();
    
    @Accessor("vertices")
    void littletilesSetVertices(int value);
    
    @Accessor("format")
    VertexFormat littletilesGetFormat();
    
    @Invoker("ensureCapacity")
    void littletilesEnsureCapacity(int size);
}
