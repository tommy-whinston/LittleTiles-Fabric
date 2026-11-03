package team.creative.creativecore.client.render.model;

import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.client.render.box.RenderBox;

/**
 * Temporary Fabric-side stand-in for CreativeCore's Forge item model. The real model
 * baking and rendering will be implemented on top of Fabric's model API.
 */
public class CreativeItemModel {

    protected final ModelResourceLocation location;

    public CreativeItemModel(ModelResourceLocation location) {
        this.location = location;
    }

    public List<? extends RenderBox> getBoxes(ItemStack stack, boolean translucent) {
        return Collections.emptyList();
    }

    public boolean hasTranslucentLayer(ItemStack stack) {
        return false;
    }

    public void saveCachedModel(boolean translucent, List<BakedQuad> cachedQuads, ItemStack stack, boolean threaded) {
    }

    public List<BakedQuad> getCachedModel(boolean translucent, ItemStack stack, boolean threaded) {
        return null;
    }

    public void applyCustomOpenGLHackery(PoseStack pose, ItemStack stack, ItemDisplayContext context) {
    }
}
