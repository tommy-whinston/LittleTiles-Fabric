package team.creative.littletiles.client.render.block;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import team.creative.littletiles.api.common.block.ILittleMCBlock;
import team.creative.littletiles.api.common.block.LittleBlock;

public class LittleBlockClientRegistry {
    
    private static final ChunkRenderTypeSet SOLID = ChunkRenderTypeSet.of(RenderType.solid());
    private static final Map<LittleBlock, ChunkRenderTypeSet> CACHED_LAYERS = Collections.synchronizedMap(new HashMap<>());
    
    public static boolean canRenderInLayer(LittleBlock block, RenderType layer) {
        ChunkRenderTypeSet layers = CACHED_LAYERS.get(block);
        if (layers == null) {
            if (block.shouldUseStateForRenderType())
                Minecraft.getInstance().executeBlocking(() -> CACHED_LAYERS.put(block, ChunkRenderTypeSet.of(net.minecraft.client.renderer.ItemBlockRenderTypes.getRenderType(block
                        .getState(), false))));
            else
                Minecraft.getInstance().executeBlocking(() -> CACHED_LAYERS.put(block, SOLID));
            layers = CACHED_LAYERS.get(block);
        }
        if (layers == null)
            throw new IllegalArgumentException();
        return layers.contains(layer);
    }
    
    public static void clearCache() {
        CACHED_LAYERS.clear();
    }
    
}
