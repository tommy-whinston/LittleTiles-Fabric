package team.creative.littletiles.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.minecraftforge.common.ForgeHooks;
import team.creative.littletiles.api.common.tool.ILittleTool;
import team.creative.littletiles.LittleTiles;
import team.creative.littletiles.client.LittleTilesClient;
import team.creative.littletiles.common.math.vec.LittleHitResult;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    
    @Unique
    public Minecraft asMinecraft() {
        return (Minecraft) (Object) this;
    }
    
    @Inject(method = "continueAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"), require = 1,
            cancellable = true)
    private void continueAttack(boolean holding, CallbackInfo info) {
        Minecraft mc = asMinecraft();
        if (holding && mc.hitResult instanceof LittleHitResult result && result.hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = result.asBlockHit();
            BlockPos blockpos = blockhitresult.getBlockPos();
            if (!result.level.isEmptyBlock(blockpos)) {
                var inputEvent = ForgeHooksClient.onClickInput(0, mc.options.keyAttack, InteractionHand.MAIN_HAND);
                if (inputEvent.isCanceled()) {
                    if (inputEvent.shouldSwingHand()) {
                        mc.player.swing(InteractionHand.MAIN_HAND);
                    }
                    info.cancel();
                    return;
                }
                Direction direction = blockhitresult.getDirection();
                if (LittleTilesClient.INTERACTION_HANDLER != null && LittleTilesClient.INTERACTION_HANDLER.continueDestroyBlock(result.level.asLevel(), blockpos, direction) && inputEvent.shouldSwingHand()) {
                    mc.player.swing(InteractionHand.MAIN_HAND);
                }
            }
            info.cancel();
        }
    }
    
    @Inject(method = "continueAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;stopDestroyBlock()V"), require = 1)
    private void continueAttackStop(CallbackInfo info) {
        if (LittleTilesClient.INTERACTION_HANDLER != null) LittleTilesClient.INTERACTION_HANDLER.stopDestroyBlock();
    }
    
    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"), require = 1,
            cancellable = true)
    private void startAttack(CallbackInfoReturnable<Boolean> info) {
        Minecraft mc = asMinecraft();
        if (mc.hitResult instanceof LittleHitResult hit) {
            if (hit.isEntity()) {
                if (LittleTilesClient.INTERACTION_HANDLER != null) LittleTilesClient.INTERACTION_HANDLER.attack(hit.level.asLevel(), mc.player, hit.asEntityHit().getEntity());
                info.setReturnValue(false);
            }
            
            BlockHitResult blockhitresult = hit.asBlockHit();
            BlockPos blockpos = blockhitresult.getBlockPos();
            if (!hit.level.isEmptyBlock(blockpos)) {
                if (LittleTilesClient.INTERACTION_HANDLER != null) LittleTilesClient.INTERACTION_HANDLER.startDestroyBlock(hit.level.asLevel(), blockpos, blockhitresult.getDirection());
                info.setReturnValue(hit.level.getBlockState(blockpos).isAir());
            }
            
        }
    }
    
    @Inject(method = "pickBlock", at = @At("HEAD"), cancellable = true)
    private void pickBlock(CallbackInfo info) {
        Minecraft mc = asMinecraft();
        if (mc.player == null)
            return;
        InteractionKeyMappingTriggered event = ForgeHooksClient.onClickInput(2, mc.options.keyPickItem, InteractionHand.MAIN_HAND);
        if (event.isCanceled()) {
            if (event.shouldSwingHand())
                mc.player.swing(InteractionHand.MAIN_HAND);
            info.cancel();
        }
    }
    
    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void startUseItem(CallbackInfo info) {
        Minecraft mc = asMinecraft();
        if (mc.player == null || mc.level == null || mc.hitResult == null || LittleTilesClient.INTERACTION_HANDLER == null)
            return;
        if (mc.gameMode.isDestroying() || mc.player.isHandsBusy())
            return;
        if (!(mc.hitResult instanceof BlockHitResult blockhitresult))
            return;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = mc.player.getItemInHand(hand);
            if (!(stack.getItem() instanceof ILittleTool))
                continue;
            if (!(stack.getItem() instanceof team.creative.littletiles.api.common.tool.ILittlePlacer placer) || !placer.hasTiles(stack))
                return;
            LittleTiles.LOGGER.info("LT tool useItemOn hand={} stack={}", hand, stack);
            InteractionResult result = LittleTilesClient.INTERACTION_HANDLER.useItemOn(mc.level, mc.player, hand, blockhitresult);
            LittleTiles.LOGGER.info("LT tool useItemOn result={}", result);
            if (result.shouldSwing())
                mc.player.swing(hand);
            info.cancel();
            return;
        }
    }
    
    @WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isDestroying()Z"), require = 1)
    private boolean isDestroying(MultiPlayerGameMode mode, Operation<Boolean> original) {
        return original.call(mode) || (LittleTilesClient.INTERACTION_HANDLER != null && LittleTilesClient.INTERACTION_HANDLER.isDestroying());
    }
    
    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void startAttackHead(CallbackInfoReturnable<Boolean> info) {
        Minecraft mc = asMinecraft();
        if (mc.player == null || mc.hitResult == null)
            return;
        ItemStack stack = mc.player.getMainHandItem();
        if (!(stack.getItem() instanceof ILittleTool))
            return;
        if (mc.hitResult instanceof BlockHitResult blockhitresult) {
            ForgeHooks.onLeftClickBlock(mc.player, blockhitresult.getBlockPos(), blockhitresult.getDirection());
            info.setReturnValue(false);
        }
    }
    
}
