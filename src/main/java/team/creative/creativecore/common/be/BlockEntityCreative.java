package team.creative.creativecore.common.be;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Fabric replacement for CreativeCore's Forge-only BlockEntityCreative.
 * Kept in the original package so the upstream LittleTiles source can remain unchanged.
 */
public abstract class BlockEntityCreative extends BlockEntity {

    public BlockEntityCreative(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public boolean isClient() {
        if (level != null)
            return level.isClientSide;
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdate(pkt.getTag(), false);
    }

    public void handleUpdateTag(CompoundTag tag) {
        handleUpdate(tag, true);
    }

    public void onChunkUnloaded() {
    }

    public abstract void handleUpdate(CompoundTag nbt, boolean chunkUpdate);

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    public void markDirty() {
        level.blockEntityChanged(worldPosition);
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }
}
