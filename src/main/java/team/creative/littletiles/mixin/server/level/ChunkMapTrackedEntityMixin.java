package team.creative.littletiles.mixin.server.level;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import team.creative.littletiles.common.entity.LittleEntity;

@Mixin(targets = "net.minecraft.server.level.ChunkMap$TrackedEntity")
public abstract class ChunkMapTrackedEntityMixin {
    
    @Shadow
    @Final
    public Entity entity;
    
    @Shadow
    @Final
    private Set<ServerPlayerConnection> seenBy;
    
    @Unique
    private boolean littletilesWasTracking;
    
    @Inject(method = "updatePlayer", at = @At("HEAD"))
    private void littletilesBeforeUpdatePlayer(ServerPlayer player, CallbackInfo info) {
        littletilesWasTracking = seenBy.stream().anyMatch(x -> x.getPlayer() == player);
    }
    
    @Inject(method = "updatePlayer", at = @At("TAIL"))
    private void littletilesAfterUpdatePlayer(ServerPlayer player, CallbackInfo info) {
        boolean tracking = seenBy.stream().anyMatch(x -> x.getPlayer() == player);
        if (littletilesWasTracking == tracking)
            return;
        
        if (entity instanceof LittleEntity) {
            if (tracking)
                MinecraftForge.EVENT_BUS.post(new PlayerEvent.StartTracking(player, entity));
            else
                MinecraftForge.EVENT_BUS.post(new PlayerEvent.StopTracking(player, entity));
        }
    }
}
