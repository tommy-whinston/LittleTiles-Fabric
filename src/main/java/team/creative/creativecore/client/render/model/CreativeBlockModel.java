package team.creative.creativecore.client.render.model;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import team.creative.creativecore.client.render.box.RenderBox;

public class CreativeBlockModel {

    public List<? extends RenderBox> getBoxes(BlockState state, ModelData data, RandomSource source) {
        return Collections.emptyList();
    }

    public ModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, ModelData modelData) {
        return modelData;
    }

    public List<BakedQuad> getBakedQuad(Object holder, Object level, BlockPos pos, BlockPos offset, BlockState state, Object blockModel,
            ModelData modelData, Object facing, Object layer, RandomSource rand, boolean overrideTint, int defaultColor) {
        return Collections.emptyList();
    }
}
