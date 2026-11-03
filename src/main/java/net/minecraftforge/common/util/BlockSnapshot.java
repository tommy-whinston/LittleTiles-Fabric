package net.minecraftforge.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSnapshot {

    private final ResourceKey<Level> dimension;
    private final BlockPos pos;
    private final BlockState state;
    private final Level level;

    private BlockSnapshot(ResourceKey<Level> dimension, Level level, BlockPos pos, BlockState state) {
        this.dimension = dimension;
        this.level = level;
        this.pos = pos;
        this.state = state;
    }

    public static BlockSnapshot create(ResourceKey<Level> dimension, Level level, BlockPos pos) {
        return new BlockSnapshot(dimension, level, pos, level.getBlockState(pos));
    }

    public ResourceKey<Level> getDimension() {
        return dimension;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isRemoved() {
        return false;
    }

    public ServerLevel getServerLevel() {
        return level instanceof ServerLevel server ? server : null;
    }
}
