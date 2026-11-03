package net.minecraftforge.client.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;

public class RenderBlockScreenEffectEvent extends Event {

    public enum OverlayType {
        WATER,
        FIRE,
        NONE
    }

    private final OverlayType overlayType;
    private final Player player;
    private final BlockState blockState;
    private final BlockPos blockPos;

    public RenderBlockScreenEffectEvent() {
        this.overlayType = null;
        this.player = null;
        this.blockState = null;
        this.blockPos = null;
    }

    public RenderBlockScreenEffectEvent(OverlayType overlayType, Player player, BlockState blockState, BlockPos blockPos) {
        this.overlayType = overlayType;
        this.player = player;
        this.blockState = blockState;
        this.blockPos = blockPos;
    }

    public OverlayType getOverlayType() {
        return overlayType;
    }

    public Player getPlayer() {
        return player;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
