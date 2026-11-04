package team.creative.littletiles.client.render.item;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.client.render.box.RenderBox;
import team.creative.littletiles.api.common.tool.ILittlePlacer;
import team.creative.littletiles.common.block.little.element.LittleElement;
import team.creative.littletiles.common.block.little.tile.group.LittleGroup;
import team.creative.littletiles.common.item.ItemLittleChisel;

public class LittleItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    
    private static final ModelResourceLocation CHISEL_BACKGROUND = new ModelResourceLocation("littletiles", "chisel_background", "inventory");
    
    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        if (stack.getItem() instanceof ItemLittleChisel) {
            Minecraft mc = Minecraft.getInstance();
            LittleElement element = ItemLittleChisel.getElement(stack);
            BakedModel background = mc.getModelManager().getModel(CHISEL_BACKGROUND);
            mc.getItemRenderer().render(stack, mode, false, pose, buffer, light, overlay, background);
            pose.pushPose();
            pose.translate(0.1F, 0.1F, 0.2F);
            pose.scale(0.7F, 0.7F, 0.7F);
            mc.getBlockRenderer().renderSingleBlock(element.getState(), pose, buffer, light, overlay);
            pose.popPose();
            return;
        }
        
        if (!(stack.getItem() instanceof ILittlePlacer placer))
            return;
        
        LittleGroup group = placer.getTiles(stack);
        if (group == null || group.isEmptyIncludeChildren()) {
            pose.pushPose();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), pose, buffer, light, overlay);
            pose.popPose();
            return;
        }
        
        List<RenderBox> boxes = group.getRenderingBoxes(false);
        boxes.addAll(group.getRenderingBoxes(true));
        if (boxes.isEmpty())
            return;
        
        LittleGroup.shrinkCubesToOneBlock(boxes);
        var blockRenderer = Minecraft.getInstance().getBlockRenderer();
        for (RenderBox box : boxes) {
            if (box.state == null || box.state.isAir())
                continue;
            float sizeX = box.maxX - box.minX;
            float sizeY = box.maxY - box.minY;
            float sizeZ = box.maxZ - box.minZ;
            if (sizeX <= 0 || sizeY <= 0 || sizeZ <= 0)
                continue;
            
            pose.pushPose();
            pose.translate(box.minX, box.minY, box.minZ);
            pose.scale(sizeX, sizeY, sizeZ);
            blockRenderer.renderSingleBlock(box.state, pose, buffer, light, overlay);
            pose.popPose();
        }
    }
    
}
