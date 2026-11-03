package team.creative.creativecore.client.render.model;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import team.creative.creativecore.client.render.box.RenderBox;
import team.creative.creativecore.common.util.math.base.Facing;

public class CreativeBakedBoxModel implements BakedModel {

    public CreativeItemBoxModel item;

    public static void compileBoxes(List<? extends RenderBox> boxes, Facing facing, RenderType layer, RandomSource rand, boolean translucent,
            List<BakedQuad> quads) {
    }

    public List<BakedQuad> getQuads(BlockState state, Direction direction, RandomSource random) {
        return Collections.emptyList();
    }

    public boolean useAmbientOcclusion() {
        return true;
    }

    public boolean isGui3d() {
        return true;
    }

    public boolean usesBlockLight() {
        return true;
    }

    public boolean isCustomRenderer() {
        return false;
    }

    public TextureAtlasSprite getParticleIcon() {
        return null;
    }

    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
