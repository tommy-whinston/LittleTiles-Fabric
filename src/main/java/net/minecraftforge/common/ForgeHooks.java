package net.minecraftforge.common;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public final class ForgeHooks {

    private ForgeHooks() {
    }

    public static PlayerInteractEvent.LeftClickBlock onLeftClickBlock(Player player, BlockPos pos, Direction direction) {
        PlayerInteractEvent.LeftClickBlock event = new PlayerInteractEvent.LeftClickBlock(player, pos, direction);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static PlayerInteractEvent.RightClickBlock onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hit) {
        PlayerInteractEvent.RightClickBlock event = new PlayerInteractEvent.RightClickBlock(player, hand, pos, hit);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static boolean isCorrectToolForDrops(BlockState state, Player player) {
        return player.hasCorrectToolForDrops(state);
    }

    public static InteractionResult onInteractEntityAt(Player player, Entity entity, EntityHitResult hit, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public static InteractionResult onItemRightClick(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public static void onDifficultyChange(net.minecraft.world.Difficulty difficulty, net.minecraft.world.Difficulty newDifficulty) {
    }


    public static Optional<BlockPos> isLivingOnLadder(BlockState state, Level level, BlockPos pos, LivingEntity entity) {
        if (state.is(BlockTags.CLIMBABLE))
            return Optional.of(pos);
        return Optional.empty();
    }

    public static Event.Result getUseItem(PlayerInteractEvent event) {
        return event.getUseItem();
    }
}
