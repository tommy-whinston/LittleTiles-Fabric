package net.minecraftforge.event.level;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.eventbus.api.Event;

public class BlockEvent extends Event {

    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;

    public BlockEvent() {
        this.level = null;
        this.pos = null;
        this.state = null;
    }

    public BlockEvent(LevelAccessor level, BlockPos pos, BlockState state) {
        this.level = level;
        this.pos = pos;
        this.state = state;
    }

    public LevelAccessor getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public static class BreakEvent extends BlockEvent {

        private final Player player;

        public BreakEvent() {
            this.player = null;
        }

        public BreakEvent(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
            super(level, pos, state);
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public int getExpToDrop() {
            return 0;
        }
    }

    public static class EntityMultiPlaceEvent extends BlockEvent {

        private final List<BlockSnapshot> snapshots;
        private final Entity entity;

        public EntityMultiPlaceEvent() {
            this.snapshots = null;
            this.entity = null;
        }

        public EntityMultiPlaceEvent(List<BlockSnapshot> snapshots, BlockState state, Entity entity) {
            super(entity.level(), entity.blockPosition(), state);
            this.snapshots = snapshots;
            this.entity = entity;
        }

        public List<BlockSnapshot> getReplacedBlockSnapshots() {
            return snapshots;
        }

        public Entity getEntity() {
            return entity;
        }
    }
}
