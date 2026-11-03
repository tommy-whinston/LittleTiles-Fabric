package net.minecraftforge.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class SleepingLocationCheckEvent extends PlayerEvent {

    private final BlockPos sleepingLocation;

    public SleepingLocationCheckEvent() {
        this.sleepingLocation = null;
    }

    public SleepingLocationCheckEvent(Player player, BlockPos sleepingLocation) {
        super(player);
        this.sleepingLocation = sleepingLocation;
    }

    public BlockPos getSleepingLocation() {
        return sleepingLocation;
    }
}
