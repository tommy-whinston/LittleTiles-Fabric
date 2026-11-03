package net.minecraftforge.event.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.Event;

public class PlayerEvent extends Event {

    private final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getEntity() {
        return player;
    }

    public static class BreakSpeed extends PlayerEvent {

        private final BlockState state;
        private final BlockPos pos;
        private float newSpeed;

        public BreakSpeed(Player player, BlockState state, float originalSpeed, BlockPos pos) {
            super(player);
            this.state = state;
            this.pos = pos;
            this.newSpeed = originalSpeed;
        }

        public BlockState getState() {
            return state;
        }

        public BlockPos getPos() {
            return pos;
        }

        public float getNewSpeed() {
            return newSpeed;
        }

        public void setNewSpeed(float newSpeed) {
            this.newSpeed = newSpeed;
        }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {

        public PlayerLoggedInEvent(Player player) {
            super(player);
        }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {

        public PlayerLoggedOutEvent(Player player) {
            super(player);
        }
    }

    public static class StartTracking extends PlayerEvent {

        private final Entity target;

        public StartTracking(Player player, Entity target) {
            super(player);
            this.target = target;
        }

        public Entity getTarget() {
            return target;
        }
    }

    public static class StopTracking extends PlayerEvent {

        private final Entity target;

        public StopTracking(Player player, Entity target) {
            super(player);
            this.target = target;
        }

        public Entity getTarget() {
            return target;
        }
    }
}
