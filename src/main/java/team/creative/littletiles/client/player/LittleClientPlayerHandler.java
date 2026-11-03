package team.creative.littletiles.client.player;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.resources.sounds.GuardianAttackSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.phys.Vec3;
import team.creative.littletiles.LittleTiles;
import team.creative.littletiles.client.LittleTilesClient;
import team.creative.littletiles.client.level.ClientLevelExtender;
import team.creative.littletiles.client.level.little.LittleClientLevel;
import team.creative.littletiles.common.entity.animation.LittleAnimationLevel;
import team.creative.littletiles.common.level.little.LittleLevel;
import team.creative.littletiles.common.packet.entity.LittleVanillaPacket;
import team.creative.littletiles.mixin.client.network.ClientPacketListenerAccessor;

public class LittleClientPlayerHandler implements TickablePacketListener, ClientGamePacketListener {
    
    private static final Logger LOGGER = LittleTiles.LOGGER;
    private static final Minecraft mc = Minecraft.getInstance();
    public Level level;
    
    public LittleClientLevel requiresClientLevel() {
        if (level instanceof LittleClientLevel s)
            return s;
        throw new RuntimeException("Cannot run this packet on this level " + level);
    }
    
    public ClientLevelExtender requiresClientLevelExtender() {
        if (level instanceof ClientLevelExtender s)
            return s;
        throw new RuntimeException("Cannot run this packet on this level " + level);
    }
    
    public void ensureRunningOnSameThread(Packet packet) throws RunningOnDifferentThreadException {
        if (!mc.isSameThread()) {
            mc.executeIfPossible(() -> LittleTilesClient.PLAYER_CONNECTION.runInContext((LittleLevel) level, x -> packet.handle(x)));
            throw RunningOnDifferentThreadException.RUNNING_ON_DIFFERENT_THREAD;
        }
    }
    
    public ClientPacketListener vanilla() {
        return Minecraft.getInstance().getConnection();
    }
    
    public ClientPacketListenerAccessor vanillaAccessor() {
        return (ClientPacketListenerAccessor) Minecraft.getInstance().getConnection();
    }
    
    public void handleLogin(ClientboundLoginPacket packet) {
        vanilla().handleLogin(packet);
    }
    
    public void handleDisconnect(ClientboundDisconnectPacket packet) {
        vanilla().handleDisconnect(packet);
    }
    
    public void onDisconnect(Component component) {
        vanilla().onDisconnect(component);
    }
    
    public void handleRespawn(ClientboundRespawnPacket packet) {
        vanilla().handleRespawn(packet);
    }
    
    public void handleResourcePack(ClientboundResourcePackPacket packet) {
        vanilla().handleResourcePack(packet);
    }
    
    public void handlePlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket packet) {
        vanilla().handlePlayerInfoUpdate(packet);
    }
    
    public void handlePlayerInfoRemove(ClientboundPlayerInfoRemovePacket packet) {
        vanilla().handlePlayerInfoRemove(packet);
    }
    
    public void handleBlockDestruction(ClientboundBlockDestructionPacket packet) {
        ensureRunningOnSameThread(packet);
        level.destroyBlockProgress(packet.getId(), packet.getPos(), packet.getProgress());
    }
    
    public void handleExplosion(ClientboundExplodePacket packet) {
        ensureRunningOnSameThread(packet);
        Explosion explosion = new Explosion(level, (Entity) null, packet.getX(), packet.getY(), packet.getZ(), packet.getPower(), packet.getToBlow());
        explosion.finalizeExplosion(true);
        mc.player.setDeltaMovement(mc.player.getDeltaMovement().add(packet.getKnockbackX(), packet.getKnockbackY(), packet.getKnockbackZ()));
    }
    
    public void handleBlockEntityData(ClientboundBlockEntityDataPacket packet) {
        ensureRunningOnSameThread(packet);
        BlockPos blockpos = packet.getPos();
        level.getBlockEntity(blockpos, packet.getType()).ifPresent((x) -> {
            if (x instanceof team.creative.creativecore.common.be.BlockEntityCreative be) be.onDataPacket(vanilla().getConnection(), packet);
            
            if (x instanceof CommandBlockEntity && mc.screen instanceof CommandBlockEditScreen screen)
                screen.updateGui();
        });
    }
    
    public void handleBlockEvent(ClientboundBlockEventPacket packet) {
        ensureRunningOnSameThread(packet);
        level.blockEvent(packet.getPos(), packet.getBlock(), packet.getB0(), packet.getB1());
    }
    
    public void handleLevelEvent(ClientboundLevelEventPacket packet) {
        ensureRunningOnSameThread(packet);
        if (packet.isGlobalEvent())
            level.globalLevelEvent(packet.getType(), packet.getPos(), packet.getData());
        else
            level.levelEvent(packet.getType(), packet.getPos(), packet.getData());
        
    }
    
    public void handleSoundEvent(ClientboundSoundPacket packet) {
        ensureRunningOnSameThread(packet);
        level.playSeededSound(mc.player, packet.getX(), packet.getY(), packet.getZ(), packet.getSound(), packet.getSource(), packet.getVolume(), packet.getPitch(), packet
                .getSeed());
    }
    
    public void handleSoundEntityEvent(ClientboundSoundEntityPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = level.getEntity(packet.getId());
        if (entity != null)
            level.playSeededSound(mc.player, entity, packet.getSound(), packet.getSource(), packet.getVolume(), packet.getPitch(), packet.getSeed());
    }
    
    public void send(Packet<?> packet) {
        LittleTiles.NETWORK.sendToServer(new LittleVanillaPacket((LittleLevel) level, packet));
    }
    
    public void send(Packet<?> packet, @Nullable PacketSendListener listener) {
        send(packet);
        if (listener != null)
            listener.onSuccess();
    }
    
    public void handleAddEntity(ClientboundAddEntityPacket packet) {
        ensureRunningOnSameThread(packet);
        
        Entity entity = LittleTilesClient.ANIMATION_HANDLER.pollEntityInTransition(packet);
        if (entity == null)
            entity = packet.getType().create(this.level);
        
        if (entity != null) {
            entity.recreateFromPacket(packet);
            int i = packet.getId();
            if (level instanceof LittleAnimationLevel a)
                a.addFreshEntityFromPacket(entity);
            else
                requiresClientLevel().putNonPlayerEntity(i, entity);
            vanillaAccessor().callPostAddEntitySoundInstance(entity);
        } else
            LOGGER.warn("Skipping Entity with id {}", packet.getType());
    }
    
    public void handleAddExperienceOrb(ClientboundAddExperienceOrbPacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        Entity entity = new ExperienceOrb(this.level, packet.getX(), packet.getY(), packet.getZ(), packet.getValue());
        entity.syncPacketPositionCodec(packet.getX(), packet.getY(), packet.getZ());
        entity.setYRot(0.0F);
        entity.setXRot(0.0F);
        entity.setId(packet.getId());
        level.putNonPlayerEntity(packet.getId(), entity);
    }
    
    public void handleSetEntityMotion(ClientboundSetEntityMotionPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getId());
        if (entity != null)
            entity.lerpMotion(packet.getXa() / 8000.0D, packet.getYa() / 8000.0D, packet.getZa() / 8000.0D);
    }
    
    public void handleSetEntityData(ClientboundSetEntityDataPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.id());
        if (entity != null)
            entity.getEntityData().assignValues(packet.packedItems());
    }
    
    public void handleAddPlayer(ClientboundAddPlayerPacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        PlayerInfo playerinfo = vanilla().getPlayerInfo(packet.getPlayerId());
        if (playerinfo == null) {
            LOGGER.warn("Server attempted to add player prior to sending player info (Player id {})", packet.getPlayerId());
            return;
        }
        
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        float f = packet.getyRot() * 360 / 256.0F;
        float f1 = packet.getxRot() * 360 / 256.0F;
        int i = packet.getEntityId();
        RemotePlayer remoteplayer = new RemotePlayer(mc.level, playerinfo.getProfile());
        remoteplayer.setId(i);
        remoteplayer.syncPacketPositionCodec(d0, d1, d2);
        remoteplayer.absMoveTo(d0, d1, d2, f, f1);
        remoteplayer.setOldPosAndRot();
        level.addPlayer(i, remoteplayer);
    }
    
    public void handleTeleportEntity(ClientboundTeleportEntityPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getId());
        if (entity != null) {
            double d0 = packet.getX();
            double d1 = packet.getY();
            double d2 = packet.getZ();
            entity.syncPacketPositionCodec(d0, d1, d2);
            if (!entity.isControlledByLocalInstance()) {
                float f = packet.getyRot() * 360 / 256.0F;
                float f1 = packet.getxRot() * 360 / 256.0F;
                entity.lerpTo(d0, d1, d2, f, f1, 3, true);
                entity.setOnGround(packet.isOnGround());
            }
            
        }
    }
    
    public void handleSetCarriedItem(ClientboundSetCarriedItemPacket packet) {
        vanilla().handleSetCarriedItem(packet);
    }
    
    public void handleMoveEntity(ClientboundMoveEntityPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = packet.getEntity(this.level);
        if (entity != null) {
            if (!entity.isControlledByLocalInstance()) {
                if (packet.hasPosition()) {
                    VecDeltaCodec vecdeltacodec = entity.getPositionCodec();
                    Vec3 vec3 = vecdeltacodec.decode(packet.getXa(), packet.getYa(), packet.getZa());
                    vecdeltacodec.setBase(vec3);
                    float f = packet.hasRotation() ? packet.getyRot() * 360 / 256.0F : entity.getYRot();
                    float f1 = packet.hasRotation() ? packet.getxRot() * 360 / 256.0F : entity.getXRot();
                    entity.lerpTo(vec3.x(), vec3.y(), vec3.z(), f, f1, 3, false);
                } else if (packet.hasRotation()) {
                    float f2 = packet.getyRot() * 360 / 256.0F;
                    float f3 = packet.getxRot() * 360 / 256.0F;
                    entity.lerpTo(entity.getX(), entity.getY(), entity.getZ(), f2, f3, 3, false);
                }
                
                entity.setOnGround(packet.isOnGround());
            }
            
        }
    }
    
    public void handleRotateMob(ClientboundRotateHeadPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = packet.getEntity(this.level);
        if (entity != null) {
            float f = packet.getYHeadRot() * 360 / 256.0F;
            entity.lerpHeadTo(f, 3);
        }
    }
    
    public void handleRemoveEntities(ClientboundRemoveEntitiesPacket packet) {
        ensureRunningOnSameThread(packet);
        packet.getEntityIds().forEach(id -> ((LittleLevel) level).removeEntityById(id, Entity.RemovalReason.DISCARDED));
    }
    
    public void handleMovePlayer(ClientboundPlayerPositionPacket packet) {
        ensureRunningOnSameThread(packet);
        Player player = mc.player;
        Vec3 vec3 = player.getDeltaMovement();
        boolean flag = packet.getRelativeArguments().contains(RelativeMovement.X);
        boolean flag1 = packet.getRelativeArguments().contains(RelativeMovement.Y);
        boolean flag2 = packet.getRelativeArguments().contains(RelativeMovement.Z);
        double d0;
        double d1;
        if (flag) {
            d0 = vec3.x();
            d1 = player.getX() + packet.getX();
            player.xOld += packet.getX();
            player.xo += packet.getX();
        } else {
            d0 = 0.0D;
            d1 = packet.getX();
            player.xOld = d1;
            player.xo = d1;
        }
        
        double d2;
        double d3;
        if (flag1) {
            d2 = vec3.y();
            d3 = player.getY() + packet.getY();
            player.yOld += packet.getY();
            player.yo += packet.getY();
        } else {
            d2 = 0.0D;
            d3 = packet.getY();
            player.yOld = d3;
            player.yo = d3;
        }
        
        double d4;
        double d5;
        if (flag2) {
            d4 = vec3.z();
            d5 = player.getZ() + packet.getZ();
            player.zOld += packet.getZ();
            player.zo += packet.getZ();
        } else {
            d4 = 0.0D;
            d5 = packet.getZ();
            player.zOld = d5;
            player.zo = d5;
        }
        
        player.setPos(d1, d3, d5);
        player.setDeltaMovement(d0, d2, d4);
        float f = packet.getYRot();
        float f1 = packet.getXRot();
        if (packet.getRelativeArguments().contains(RelativeMovement.X_ROT)) {
            player.setXRot(player.getXRot() + f1);
            player.xRotO += f1;
        } else {
            player.setXRot(f1);
            player.xRotO = f1;
        }
        
        if (packet.getRelativeArguments().contains(RelativeMovement.Y_ROT)) {
            player.setYRot(player.getYRot() + f);
            player.yRotO += f;
        } else {
            player.setYRot(f);
            player.yRotO = f;
        }
        
        send(new ServerboundAcceptTeleportationPacket(packet.getId()));
        send(new ServerboundMovePlayerPacket.PosRot(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot(), false));
    }
    
    public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        packet.runUpdates((p_205524_, p_205525_) -> level.setServerVerifiedBlockState(p_205524_, p_205525_, 19));
    }
    
    public void handleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        this.updateLevelChunk(level, packet.getX(), packet.getZ(), packet.getChunkData());
        this.queueLightUpdate(level, packet.getX(), packet.getZ(), packet.getLightData());
    }
    
    public void handleChunksBiomes(ClientboundChunksBiomesPacket packet) {
        ensureRunningOnSameThread(packet);
        LittleClientLevel level = requiresClientLevel();
        for (ClientboundChunksBiomesPacket.ChunkBiomeData clientboundchunksbiomespacket$chunkbiomedata : packet.chunkBiomeData())
            level.getChunkSource().replaceBiomes(clientboundchunksbiomespacket$chunkbiomedata.pos().x, clientboundchunksbiomespacket$chunkbiomedata
                    .pos().z, clientboundchunksbiomespacket$chunkbiomedata.getReadBuffer());
        
        for (ClientboundChunksBiomesPacket.ChunkBiomeData clientboundchunksbiomespacket$chunkbiomedata1 : packet.chunkBiomeData())
            level.onChunkLoaded(new ChunkPos(clientboundchunksbiomespacket$chunkbiomedata1.pos().x, clientboundchunksbiomespacket$chunkbiomedata1.pos().z));
        
        for (ClientboundChunksBiomesPacket.ChunkBiomeData clientboundchunksbiomespacket$chunkbiomedata2 : packet.chunkBiomeData())
            for (int i = -1; i <= 1; ++i)
                for (int j = -1; j <= 1; ++j)
                    for (int k = this.level.getMinSection(); k < this.level.getMaxSection(); ++k)
                        level.setSectionDirty(clientboundchunksbiomespacket$chunkbiomedata2.pos().x + i, k, clientboundchunksbiomespacket$chunkbiomedata2.pos().z + j);
    }
    
    private void updateLevelChunk(ClientLevel level, int x, int z, ClientboundLevelChunkPacketData data) {
        level.getChunkSource().replaceWithPacketData(x, z, data.getReadBuffer(), data.getHeightmaps(), data.getBlockEntitiesTagsConsumer(x, z));
    }
    
    private void queueLightUpdate(ClientLevel level, int x, int z, ClientboundLightUpdatePacketData data) {
        level.queueLightUpdate(() -> {
            this.applyLightData(level, x, z, data);
            LevelChunk levelchunk = this.level.getChunkSource().getChunk(x, z, false);
            if (levelchunk != null) {
                this.enableChunkLight(level, levelchunk, x, z);
            }
            
        });
    }
    
    private void applyLightData(ClientLevel level, int x, int z, ClientboundLightUpdatePacketData data) {
        LevelLightEngine levellightengine = this.level.getChunkSource().getLightEngine();
        BitSet bitset = data.getSkyYMask();
        BitSet bitset1 = data.getEmptySkyYMask();
        Iterator<byte[]> iterator = data.getSkyUpdates().iterator();
        this.readSectionList(level, x, z, levellightengine, LightLayer.SKY, bitset, bitset1, iterator);
        BitSet bitset2 = data.getBlockYMask();
        BitSet bitset3 = data.getEmptyBlockYMask();
        Iterator<byte[]> iterator1 = data.getBlockUpdates().iterator();
        this.readSectionList(level, x, z, levellightengine, LightLayer.BLOCK, bitset2, bitset3, iterator1);
        levellightengine.setLightEnabled(new ChunkPos(x, z), true);
    }
    
    private void readSectionList(ClientLevel level, int x, int z, LevelLightEngine light, LightLayer layer, BitSet minSet, BitSet maxSet, Iterator<byte[]> data) {
        for (int i = 0; i < light.getLightSectionCount(); ++i) {
            int j = light.getMinLightSection() + i;
            boolean flag = minSet.get(i);
            boolean flag1 = maxSet.get(i);
            if (flag || flag1) {
                light.queueSectionData(layer, SectionPos.of(x, j, z), flag ? new DataLayer(data.next().clone()) : new DataLayer());
                level.setSectionDirtyWithNeighbors(x, j, z);
            }
        }
        
    }
    
    private void enableChunkLight(ClientLevel level, LevelChunk chunk, int x, int z) {
        LevelLightEngine levellightengine = this.level.getChunkSource().getLightEngine();
        LevelChunkSection[] alevelchunksection = chunk.getSections();
        ChunkPos chunkpos = chunk.getPos();
        
        for (int i = 0; i < alevelchunksection.length; ++i) {
            LevelChunkSection levelchunksection = alevelchunksection[i];
            int j = this.level.getSectionYFromSectionIndex(i);
            levellightengine.updateSectionStatus(SectionPos.of(chunkpos, j), levelchunksection.hasOnlyAir());
            level.setSectionDirtyWithNeighbors(x, j, z);
        }
    }
    
    public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        int i = packet.getX();
        int j = packet.getZ();
        ChunkSource chunkSource = this.level.getChunkSource();
        if (chunkSource instanceof ClientChunkCache client)
            client.drop(i, j);
        this.queueLightRemoval(level, packet);
    }
    
    private void queueLightRemoval(ClientLevel level, ClientboundForgetLevelChunkPacket packet) {
        ChunkPos chunkpos = new ChunkPos(packet.getX(), packet.getZ());
        level.queueLightUpdate(() -> {
            LevelLightEngine levellightengine = this.level.getLightEngine();
            levellightengine.setLightEnabled(chunkpos, false);
            
            for (int i = levellightengine.getMinLightSection(); i < levellightengine.getMaxLightSection(); ++i) {
                SectionPos sectionpos = SectionPos.of(chunkpos, i);
                levellightengine.queueSectionData(LightLayer.BLOCK, sectionpos, (DataLayer) null);
                levellightengine.queueSectionData(LightLayer.SKY, sectionpos, (DataLayer) null);
            }
            
            for (int j = this.level.getMinSection(); j < this.level.getMaxSection(); ++j)
                levellightengine.updateSectionStatus(SectionPos.of(chunkpos, j), true);
        });
    }
    
    public void handleBlockUpdate(ClientboundBlockUpdatePacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevelExtender level = requiresClientLevelExtender();
        level.setServerVerifiedBlockStateExtender(packet.getPos(), packet.getBlockState(), 19);
    }
    
    public void handleTakeItemEntity(ClientboundTakeItemEntityPacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        Entity entity = level.getEntity(packet.getItemId());
        LivingEntity livingentity = (LivingEntity) this.level.getEntity(packet.getPlayerId());
        if (livingentity == null)
            livingentity = mc.player;
        
        if (entity != null) {
            RandomSource random = vanillaAccessor().getRandom();
            if (entity instanceof ExperienceOrb)
                level.playLocalSound(entity.getX(), entity.getY(), entity
                        .getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.1F, (random.nextFloat() - random.nextFloat()) * 0.35F + 0.9F, false);
            else
                level.playLocalSound(entity.getX(), entity.getY(), entity
                        .getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (random.nextFloat() - random.nextFloat()) * 1.4F + 2.0F, false);
            
            mc.particleEngine.add(new ItemPickupParticle(mc.getEntityRenderDispatcher(), mc.renderBuffers(), mc.level, entity, livingentity));
            if (entity instanceof ItemEntity itemEntity) {
                ItemStack itemstack = itemEntity.getItem();
                itemstack.shrink(packet.getAmount());
                if (itemstack.isEmpty())
                    level.removeEntity(packet.getItemId(), Entity.RemovalReason.DISCARDED);
            } else if (!(entity instanceof ExperienceOrb))
                level.removeEntity(packet.getItemId(), Entity.RemovalReason.DISCARDED);
        }
        
    }
    
    public void handleSystemChat(ClientboundSystemChatPacket packet) {
        vanilla().handleSystemChat(packet);
    }
    
    public void handlePlayerChat(ClientboundPlayerChatPacket packet) {
        vanilla().handlePlayerChat(packet);
    }
    
    public void handleDisguisedChat(ClientboundDisguisedChatPacket packet) {
        vanilla().handleDisguisedChat(packet);
    }
    
    public void handleDeleteChat(ClientboundDeleteChatPacket packet) {
        handleDeleteChat(packet);
    }
    
    public void handleAnimate(ClientboundAnimatePacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getId());
        if (entity != null) {
            if (packet.getAction() == 0)
                ((LivingEntity) entity).swing(InteractionHand.MAIN_HAND);
            else if (packet.getAction() == 3)
                ((LivingEntity) entity).swing(InteractionHand.OFF_HAND);
            else if (packet.getAction() == 2)
                ((Player) entity).stopSleepInBed(false, false);
            else if (packet.getAction() == 4)
                mc.particleEngine.createTrackingEmitter(entity, ParticleTypes.CRIT);
            else if (packet.getAction() == 5)
                mc.particleEngine.createTrackingEmitter(entity, ParticleTypes.ENCHANTED_HIT);
        }
    }
    
    public void handleHurtAnimation(ClientboundHurtAnimationPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.id());
        if (entity != null)
            entity.animateHurt(packet.yaw());
    }
    
    public void handleSetTime(ClientboundSetTimePacket packet) {
        vanilla().handleSetTime(packet);
    }
    
    public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket packet) {
        vanilla().handleSetSpawn(packet);
    }
    
    public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getVehicle());
        if (entity == null) {
            LOGGER.warn("Received passengers for unknown entity");
            return;
        }
        
        Player player = mc.player;
        boolean flag = entity.hasIndirectPassenger(player);
        entity.ejectPassengers();
        
        for (int i : packet.getPassengers()) {
            Entity entity1 = this.level.getEntity(i);
            if (entity1 != null) {
                entity1.startRiding(entity, true);
                if (entity1 == player && !flag) {
                    if (entity instanceof Boat) {
                        player.yRotO = entity.getYRot();
                        player.setYRot(entity.getYRot());
                        player.setYHeadRot(entity.getYRot());
                    }
                    
                    Component component = Component.translatable("mount.onboard", mc.options.keyShift.getTranslatedKeyMessage());
                    mc.gui.setOverlayMessage(component, false);
                    mc.getNarrator().sayNow(component);
                }
            }
            
        }
    }
    
    public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getSourceId());
        if (entity instanceof Mob)
            ((Mob) entity).setDelayedLeashHolderId(packet.getDestId());
    }
    
    private static ItemStack findTotem(Player p_104928_) {
        for (InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack = p_104928_.getItemInHand(interactionhand);
            if (itemstack.is(Items.TOTEM_OF_UNDYING)) {
                return itemstack;
            }
        }
        
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }
    
    public void handleEntityEvent(ClientboundEntityEventPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = packet.getEntity(this.level);
        if (entity != null) {
            if (packet.getEventId() == 21)
                mc.getSoundManager().play(new GuardianAttackSoundInstance((Guardian) entity));
            else if (packet.getEventId() == 35) {
                mc.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                this.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);
                if (entity == mc.player)
                    mc.gameRenderer.displayItemActivation(findTotem(mc.player));
            } else
                entity.handleEntityEvent(packet.getEventId());
        }
        
    }
    
    public void handleDamageEvent(ClientboundDamageEventPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.entityId());
        if (entity != null)
            entity.handleDamageEvent(packet.getSource(this.level));
    }
    
    public void handleSetHealth(ClientboundSetHealthPacket packet) {
        vanilla().handleSetHealth(packet);
    }
    
    public void handleSetExperience(ClientboundSetExperiencePacket packet) {
        vanilla().handleSetExperience(packet);
    }
    
    public void handleHorseScreenOpen(ClientboundHorseScreenOpenPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getEntityId());
        if (entity instanceof AbstractHorse horse) {
            LocalPlayer localplayer = mc.player;
            SimpleContainer simplecontainer = new SimpleContainer(packet.getSize());
            HorseInventoryMenu horseinventorymenu = new HorseInventoryMenu(packet.getContainerId(), localplayer.getInventory(), simplecontainer, horse);
            localplayer.containerMenu = horseinventorymenu;
            mc.setScreen(new HorseInventoryScreen(horseinventorymenu, localplayer.getInventory(), horse));
        }
        
    }
    
    public void handleOpenScreen(ClientboundOpenScreenPacket packet) {
        vanilla().handleOpenScreen(packet);
    }
    
    public void handleContainerSetSlot(ClientboundContainerSetSlotPacket packet) {
        vanilla().handleContainerSetSlot(packet);
    }
    
    public void handleContainerContent(ClientboundContainerSetContentPacket packet) {
        vanilla().handleContainerContent(packet);
    }
    
    public void handleOpenSignEditor(ClientboundOpenSignEditorPacket packet) {
        ensureRunningOnSameThread(packet);
        BlockPos blockpos = packet.getPos();
        BlockEntity blockentity = this.level.getBlockEntity(blockpos);
        if (!(blockentity instanceof SignBlockEntity)) {
            BlockState blockstate = this.level.getBlockState(blockpos);
            blockentity = new SignBlockEntity(blockpos, blockstate);
            blockentity.setLevel(this.level);
        }
        
        mc.player.openTextEdit((SignBlockEntity) blockentity, packet.isFrontText());
    }
    
    public void handleContainerSetData(ClientboundContainerSetDataPacket packet) {
        vanilla().handleContainerSetData(packet);
    }
    
    public void handleSetEquipment(ClientboundSetEquipmentPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getEntity());
        if (entity != null)
            packet.getSlots().forEach((slot) -> entity.setItemSlot(slot.getFirst(), slot.getSecond()));
    }
    
    public void handleContainerClose(ClientboundContainerClosePacket packet) {
        vanilla().handleContainerClose(packet);
    }
    
    public void handleGameEvent(ClientboundGameEventPacket packet) {
        vanilla().handleGameEvent(packet);
    }
    
    public void handleMapItemData(ClientboundMapItemDataPacket packet) {
        vanilla().handleMapItemData(packet);
    }
    
    public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket packet) {
        vanilla().handleUpdateAdvancementsPacket(packet);
    }
    
    public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket packet) {
        vanilla().handleSelectAdvancementsTab(packet);
    }
    
    public void handleCommands(ClientboundCommandsPacket packet) {
        vanilla().handleCommands(packet);
    }
    
    public void handleStopSoundEvent(ClientboundStopSoundPacket packet) {
        vanilla().handleStopSoundEvent(packet);
    }
    
    public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket packet) {
        vanilla().handleCommandSuggestions(packet);;
    }
    
    public void handleUpdateRecipes(ClientboundUpdateRecipesPacket packet) {
        vanilla().handleUpdateRecipes(packet);
    }
    
    public void handleLookAt(ClientboundPlayerLookAtPacket packet) {
        ensureRunningOnSameThread(packet);
        Vec3 vec3 = packet.getPosition(this.level);
        if (vec3 != null)
            mc.player.lookAt(packet.getFromAnchor(), vec3);
    }
    
    public void handleTagQueryPacket(ClientboundTagQueryPacket packet) {
        vanilla().handleTagQueryPacket(packet);
    }
    
    public void handleAwardStats(ClientboundAwardStatsPacket packet) {
        vanilla().handleAwardStats(packet);
    }
    
    public void handleAddOrRemoveRecipes(ClientboundRecipePacket packet) {
        vanilla().handleAddOrRemoveRecipes(packet);
    }
    
    public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getEntityId());
        if (entity instanceof LivingEntity living) {
            MobEffect mobeffect = packet.getEffect();
            if (mobeffect != null)
                living.forceAddEffect(new MobEffectInstance(mobeffect, packet.getEffectDurationTicks(), packet.getEffectAmplifier(), packet.isEffectAmbient(), packet
                        .isEffectVisible(), packet.effectShowsIcon(), (MobEffectInstance) null, Optional.ofNullable(packet.getFactorData())), (Entity) null);
        }
    }
    
    public void handleUpdateTags(ClientboundUpdateTagsPacket packet) {
        vanilla().handleUpdateTags(packet);
    }
    
    public void handleEnabledFeatures(ClientboundUpdateEnabledFeaturesPacket packet) {
        vanilla().handleEnabledFeatures(packet);
    }
    
    public void handlePlayerCombatEnd(ClientboundPlayerCombatEndPacket packet) {}
    
    public void handlePlayerCombatEnter(ClientboundPlayerCombatEnterPacket packet) {}
    
    public void handlePlayerCombatKill(ClientboundPlayerCombatKillPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getPlayerId());
        if (entity == mc.player)
            if (mc.player.shouldShowDeathScreen())
                mc.setScreen(new DeathScreen(packet.getMessage(), this.level.getLevelData().isHardcore()));
            else
                mc.player.respawn();
    }
    
    public void handleChangeDifficulty(ClientboundChangeDifficultyPacket packet) {
        vanilla().handleChangeDifficulty(packet);
    }
    
    public void handleSetCamera(ClientboundSetCameraPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = packet.getEntity(this.level);
        if (entity != null)
            mc.setCameraEntity(entity);
    }
    
    public void handleInitializeBorder(ClientboundInitializeBorderPacket packet) {
        vanilla().handleInitializeBorder(packet);
    }
    
    public void handleSetBorderCenter(ClientboundSetBorderCenterPacket packet) {
        vanilla().handleSetBorderCenter(packet);
    }
    
    public void handleSetBorderLerpSize(ClientboundSetBorderLerpSizePacket packet) {
        vanilla().handleSetBorderLerpSize(packet);
    }
    
    public void handleSetBorderSize(ClientboundSetBorderSizePacket packet) {
        vanilla().handleSetBorderSize(packet);
    }
    
    public void handleSetBorderWarningDistance(ClientboundSetBorderWarningDistancePacket packet) {
        vanilla().handleSetBorderWarningDistance(packet);
    }
    
    public void handleSetBorderWarningDelay(ClientboundSetBorderWarningDelayPacket packet) {
        vanilla().handleSetBorderWarningDelay(packet);
    }
    
    public void handleTitlesClear(ClientboundClearTitlesPacket packet) {
        vanilla().handleTitlesClear(packet);
    }
    
    public void handleServerData(ClientboundServerDataPacket packet) {
        vanilla().handleServerData(packet);
    }
    
    public void handleCustomChatCompletions(ClientboundCustomChatCompletionsPacket packet) {
        vanilla().handleCustomChatCompletions(packet);
    }
    
    public void setActionBarText(ClientboundSetActionBarTextPacket packet) {
        vanilla().setActionBarText(packet);
    }
    
    public void setTitleText(ClientboundSetTitleTextPacket packet) {
        vanilla().setTitleText(packet);
    }
    
    public void setSubtitleText(ClientboundSetSubtitleTextPacket packet) {
        vanilla().setSubtitleText(packet);
    }
    
    public void setTitlesAnimation(ClientboundSetTitlesAnimationPacket packet) {
        vanilla().setTitlesAnimation(packet);
    }
    
    public void handleTabListCustomisation(ClientboundTabListPacket packet) {
        vanilla().handleTabListCustomisation(packet);
    }
    
    public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = packet.getEntity(this.level);
        if (entity instanceof LivingEntity living)
            living.removeEffectNoUpdate(packet.getEffect());
    }
    
    public void handleKeepAlive(ClientboundKeepAlivePacket packet) {
        vanilla().handleKeepAlive(packet);
    }
    
    public void handlePlayerAbilities(ClientboundPlayerAbilitiesPacket packet) {
        vanilla().handlePlayerAbilities(packet);
    }
    
    public void handleBossUpdate(ClientboundBossEventPacket packet) {
        vanilla().handleBossUpdate(packet);
    }
    
    public void handleItemCooldown(ClientboundCooldownPacket packet) {
        vanilla().handleItemCooldown(packet);
    }
    
    public void handleMoveVehicle(ClientboundMoveVehiclePacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = mc.player.getRootVehicle();
        if (entity != mc.player && entity.isControlledByLocalInstance()) {
            entity.absMoveTo(packet.getX(), packet.getY(), packet.getZ(), packet.getYRot(), packet.getXRot());
            send(new ServerboundMoveVehiclePacket(entity));
        }
    }
    
    public void handleOpenBook(ClientboundOpenBookPacket packet) {
        vanilla().handleOpenBook(packet);
    }
    
    public void handleCustomPayload(ClientboundCustomPayloadPacket packet) {
        vanilla().handleCustomPayload(packet);
    }
    
    public void handleAddObjective(ClientboundSetObjectivePacket packet) {
        vanilla().handleAddObjective(packet);
    }
    
    public void handleSetScore(ClientboundSetScorePacket packet) {
        vanilla().handleSetScore(packet);
    }
    
    public void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket packet) {
        vanilla().handleSetDisplayObjective(packet);
    }
    
    public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket packet) {
        vanilla().handleSetPlayerTeamPacket(packet);
    }
    
    public void handleParticleEvent(ClientboundLevelParticlesPacket packet) {
        ensureRunningOnSameThread(packet);
        if (packet.getCount() == 0) {
            double d0 = packet.getMaxSpeed() * packet.getXDist();
            double d2 = packet.getMaxSpeed() * packet.getYDist();
            double d4 = packet.getMaxSpeed() * packet.getZDist();
            
            try {
                this.level.addParticle(packet.getParticle(), packet.isOverrideLimiter(), packet.getX(), packet.getY(), packet.getZ(), d0, d2, d4);
            } catch (Throwable throwable1) {
                LOGGER.warn("Could not spawn particle effect {}", packet.getParticle());
            }
        } else {
            RandomSource random = vanillaAccessor().getRandom();
            for (int i = 0; i < packet.getCount(); ++i) {
                double d1 = random.nextGaussian() * packet.getXDist();
                double d3 = random.nextGaussian() * packet.getYDist();
                double d5 = random.nextGaussian() * packet.getZDist();
                double d6 = random.nextGaussian() * packet.getMaxSpeed();
                double d7 = random.nextGaussian() * packet.getMaxSpeed();
                double d8 = random.nextGaussian() * packet.getMaxSpeed();
                
                try {
                    this.level.addParticle(packet.getParticle(), packet.isOverrideLimiter(), packet.getX() + d1, packet.getY() + d3, packet.getZ() + d5, d6, d7, d8);
                } catch (Throwable throwable) {
                    LOGGER.warn("Could not spawn particle effect {}", packet.getParticle());
                    return;
                }
            }
        }
        
    }
    
    public void handlePing(ClientboundPingPacket packet) {
        vanilla().handlePing(packet);
    }
    
    public void handleUpdateAttributes(ClientboundUpdateAttributesPacket packet) {
        ensureRunningOnSameThread(packet);
        Entity entity = this.level.getEntity(packet.getEntityId());
        if (entity != null) {
            if (!(entity instanceof LivingEntity))
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
            
            AttributeMap attributemap = ((LivingEntity) entity).getAttributes();
            
            for (ClientboundUpdateAttributesPacket.AttributeSnapshot attribute : packet.getValues()) {
                AttributeInstance attributeinstance = attributemap.getInstance(attribute.getAttribute());
                if (attributeinstance == null)
                    LOGGER.warn("Entity {} does not have attribute {}", entity, BuiltInRegistries.ATTRIBUTE.getKey(attribute.getAttribute()));
                else {
                    attributeinstance.setBaseValue(attribute.getBase());
                    attributeinstance.removeModifiers();
                    
                    for (AttributeModifier attributemodifier : attribute.getModifiers())
                        attributeinstance.addTransientModifier(attributemodifier);
                }
            }
        }
    }
    
    public void handlePlaceRecipe(ClientboundPlaceGhostRecipePacket packet) {
        vanilla().handlePlaceRecipe(packet);
    }
    
    public void handleLightUpdatePacket(ClientboundLightUpdatePacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevel level = requiresClientLevel();
        level.queueLightUpdate(() -> vanillaAccessor().callApplyLightData(packet.getX(), packet.getZ(), packet.getLightData()));
    }
    
    public void handleMerchantOffers(ClientboundMerchantOffersPacket packet) {
        vanilla().handleMerchantOffers(packet);
    }
    
    public void handleSetChunkCacheRadius(ClientboundSetChunkCacheRadiusPacket packet) {
        vanilla().handleSetChunkCacheRadius(packet);
    }
    
    public void handleSetSimulationDistance(ClientboundSetSimulationDistancePacket packet) {
        vanilla().handleSetSimulationDistance(packet);
    }
    
    public void handleSetChunkCacheCenter(ClientboundSetChunkCacheCenterPacket packet) {
        vanilla().handleSetChunkCacheCenter(packet);
    }
    
    public void handleBundlePacket(ClientboundBundlePacket packet) {
        ensureRunningOnSameThread(packet);
        for (Packet<ClientGamePacketListener> sub : packet.subPackets())
            sub.handle(this);
    }
    
    public void handleBlockChangedAck(ClientboundBlockChangedAckPacket packet) {
        ensureRunningOnSameThread(packet);
        ClientLevelExtender level = requiresClientLevelExtender();
        level.handleBlockChangedAckExtender(packet.sequence());
    }
    
    public void tick() {}
    
    public boolean isAcceptingMessages() {
        return vanilla().isAcceptingMessages();
    }
}
