package net.minecraftforge.event;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ForgeEventFactory {

    private ForgeEventFactory() {
    }

    public static Player.BedSleepingProblem onPlayerSleepInBed(Player player, Optional<BlockPos> pos) {
        return null;
    }

    public static void onPlayerDestroyItem(Player player, ItemStack stack, InteractionHand hand) {
    }

    public static boolean fireSleepingTimeCheck(Player player, Optional<BlockPos> pos) {
        return true;
    }
}
