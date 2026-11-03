package net.minecraftforge.client.model.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class ModelDataManager {

    private final Map<BlockPos, ModelData> data = new HashMap<>();

    public ModelData getAt(BlockPos pos) {
        return data.getOrDefault(pos, ModelData.EMPTY);
    }

    public Map<BlockPos, ModelData> getAt(ChunkPos pos) {
        return data;
    }

    public void set(BlockPos pos, ModelData modelData) {
        data.put(pos, modelData);
    }
}
