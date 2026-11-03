package team.creative.littletiles.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import team.creative.littletiles.common.block.entity.BETiles;
import team.creative.littletiles.common.block.little.tile.LittleTile;
import team.creative.littletiles.common.block.little.tile.parent.IParentCollection;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.math.box.LittleBox;
import team.creative.littletiles.common.structure.LittleStructure;
import team.creative.littletiles.common.structure.attribute.LittleStructureAttribute;

public class BETilesRenderer implements BlockEntityRenderer<BETiles> {
    
    public boolean shouldRender(BETiles be, Vec3 vec) {
        return Vec3.atCenterOf(be.getBlockPos()).closerThan(vec, be.render.getMaxRenderDistance());
    }
    
    public boolean shouldRenderOffScreen(BETiles be) {
        AABB box = be.render.getRenderBoundingBox();
        if (box.maxX - box.minX > 16)
            return true;
        if (box.maxY - box.minY > 16)
            return true;
        if (box.maxZ - box.minZ > 16)
            return true;
        return false;
    }
    
    public void render(BETiles be, float partialTickTime, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        for (LittleStructure structure : be.loadedStructures(LittleStructureAttribute.TICK_RENDERING))
            structure.renderTick(pose, buffer, be.getBlockPos(), partialTickTime);
        
        LittleGrid grid = be.getGrid();
        if (grid == null || grid.count <= 0)
            return;
        
        float scale = 1.0F / grid.count;
        var blockRenderer = Minecraft.getInstance().getBlockRenderer();
        RandomSource random = RandomSource.create();
        for (IParentCollection group : be.groups()) {
            for (LittleTile tile : group) {
                BlockState state = tile.getState();
                if (state.isAir())
                    continue;
                
                RenderType layer = ItemBlockRenderTypes.getChunkRenderType(state);
                if (layer == null)
                    layer = RenderType.solid();
                VertexConsumer consumer = buffer.getBuffer(layer);
                BakedModel model = blockRenderer.getBlockModel(state);
                random.setSeed(state.getSeed(be.getBlockPos()));
                
                for (LittleBox box : tile) {
                    if (box.minX == 0 && box.minY == 0 && box.minZ == 0 && box.maxX == grid.count && box.maxY == grid.count && box.maxZ == grid.count) {
                        blockRenderer.renderSingleBlock(state, pose, buffer, packedLight, packedOverlay);
                        continue;
                    }
                    
                    float minX = box.minX * scale;
                    float minY = box.minY * scale;
                    float minZ = box.minZ * scale;
                    float maxX = box.maxX * scale;
                    float maxY = box.maxY * scale;
                    float maxZ = box.maxZ * scale;
                    
                    for (Direction direction : Direction.values()) {
                        for (BakedQuad quad : model.getQuads(state, direction, random)) {
                            BakedQuad cropped = cropQuad(quad, direction, minX, minY, minZ, maxX, maxY, maxZ);
                            if (cropped != null)
                                consumer.putBulkData(pose.last(), cropped, 1.0F, 1.0F, 1.0F, packedLight, packedOverlay);
                        }
                    }
                }
            }
        }
    }
    
    private static BakedQuad cropQuad(BakedQuad quad, Direction direction, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        int[] source = quad.getVertices();
        if (source.length < 32)
            return null;
        
        int axis = direction.getAxis().ordinal();
        int uAxis = (axis + 1) % 3;
        int vAxis = (axis + 2) % 3;
        
        float[] quadMin = { Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE };
        float[] quadMax = { -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE };
        for (int vertex = 0; vertex < 4; vertex++) {
            int offset = vertex * 8;
            for (int component = 0; component < 3; component++) {
                float value = Float.intBitsToFloat(source[offset + component]);
                quadMin[component] = Math.min(quadMin[component], value);
                quadMax[component] = Math.max(quadMax[component], value);
            }
        }
        
        float quadSizeU = quadMax[uAxis] - quadMin[uAxis];
        float quadSizeV = quadMax[vAxis] - quadMin[vAxis];
        if (quadSizeU <= 0 || quadSizeV <= 0)
            return null;
        
        float tileMin = axis == 0 ? minX : axis == 1 ? minY : minZ;
        float tileMinU = uAxis == 0 ? minX : uAxis == 1 ? minY : minZ;
        float tileMaxU = uAxis == 0 ? maxX : uAxis == 1 ? maxY : maxZ;
        float tileMinV = vAxis == 0 ? minX : vAxis == 1 ? minY : minZ;
        float tileMaxV = vAxis == 0 ? maxX : vAxis == 1 ? maxY : maxZ;
        float tileAxis = direction.getAxisDirection() == Direction.AxisDirection.POSITIVE ? (axis == 0 ? maxX : axis == 1 ? maxY : maxZ) : (axis == 0 ? minX : axis == 1 ? minY : minZ);
        
        float startU = (tileMinU - quadMin[uAxis]) / quadSizeU;
        float endU = (tileMaxU - quadMin[uAxis]) / quadSizeU;
        float startV = (tileMinV - quadMin[vAxis]) / quadSizeV;
        float endV = (tileMaxV - quadMin[vAxis]) / quadSizeV;
        
        float[] cornerU = new float[4];
        float[] cornerV = new float[4];
        float[] vertexU = new float[4];
        float[] vertexV = new float[4];
        for (int vertex = 0; vertex < 4; vertex++) {
            int offset = vertex * 8;
            float u = Float.intBitsToFloat(source[offset + uAxis]);
            float v = Float.intBitsToFloat(source[offset + vAxis]);
            float tu = (u - quadMin[uAxis]) / quadSizeU;
            float tv = (v - quadMin[vAxis]) / quadSizeV;
            vertexU[vertex] = tu;
            vertexV[vertex] = tv;
            int corner = (tu > 0.5F ? 1 : 0) | (tv > 0.5F ? 2 : 0);
            cornerU[corner] = Float.intBitsToFloat(source[offset + 4]);
            cornerV[corner] = Float.intBitsToFloat(source[offset + 5]);
        }
        
        int[] result = source.clone();
        for (int vertex = 0; vertex < 4; vertex++) {
            int offset = vertex * 8;
            float tu = vertexU[vertex] > 0.5F ? endU : startU;
            float tv = vertexV[vertex] > 0.5F ? endV : startV;
            
            float u = bilinear(cornerU, tu, tv);
            float v = bilinear(cornerV, tu, tv);
            
            float[] position = new float[3];
            position[uAxis] = tileMinU + vertexU[vertex] * (tileMaxU - tileMinU);
            position[vAxis] = tileMinV + vertexV[vertex] * (tileMaxV - tileMinV);
            position[axis] = tileAxis;
            
            result[offset] = Float.floatToRawIntBits(position[0]);
            result[offset + 1] = Float.floatToRawIntBits(position[1]);
            result[offset + 2] = Float.floatToRawIntBits(position[2]);
            result[offset + 4] = Float.floatToRawIntBits(u);
            result[offset + 5] = Float.floatToRawIntBits(v);
        }
        
        return new BakedQuad(result, quad.getTintIndex(), direction, quad.getSprite(), quad.isShade());
    }
    
    private static float bilinear(float[] corners, float u, float v) {
        return corners[0] * (1 - u) * (1 - v) + corners[1] * u * (1 - v) + corners[2] * (1 - u) * v + corners[3] * u * v;
    }
    
}
