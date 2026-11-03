package net.minecraftforge.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class PlayerInteractEvent extends PlayerEvent {

    private final InteractionHand hand;
    private final BlockPos pos;
    private final Direction face;
    private InteractionResult cancellationResult = InteractionResult.PASS;
    private Result useBlock = Result.DEFAULT;
    private Result useItem = Result.DEFAULT;
    private final BlockHitResult hitResult;

    public PlayerInteractEvent() {
        this(null, null, null, null, null);
    }

    public PlayerInteractEvent(Player player, InteractionHand hand, BlockPos pos, Direction face) {
        this(player, hand, pos, face, null);
    }

    public PlayerInteractEvent(Player player, InteractionHand hand, BlockPos pos, Direction face, BlockHitResult hitResult) {
        super(player);
        this.hand = hand;
        this.pos = pos;
        this.face = face;
        this.hitResult = hitResult;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction getFace() {
        return face;
    }

    public Level getLevel() {
        return getEntity().level();
    }

    public ItemStack getItemStack() {
        return getEntity().getItemInHand(hand);
    }

    public Vec3 getHitVec() {
        return hitResult != null ? hitResult.getLocation() : Vec3.atCenterOf(pos);
    }

    public Result getUseBlock() {
        return useBlock;
    }

    public void setUseBlock(Result useBlock) {
        this.useBlock = useBlock;
    }

    public Result getUseItem() {
        return useItem;
    }

    public void setUseItem(Result useItem) {
        this.useItem = useItem;
    }

    public InteractionResult getCancellationResult() {
        return cancellationResult;
    }

    public void setCancellationResult(InteractionResult result) {
        this.cancellationResult = result;
    }

    @Cancelable
    public static class LeftClickBlock extends PlayerInteractEvent {

        public LeftClickBlock() {
        }

        public LeftClickBlock(Player player, BlockPos pos, Direction face) {
            super(player, InteractionHand.MAIN_HAND, pos, face);
        }

        public LeftClickBlock(Player player, InteractionHand hand, BlockPos pos, Direction face) {
            super(player, hand, pos, face);
        }
    }

    @Cancelable
    public static class LeftClickEmpty extends PlayerInteractEvent {

        public LeftClickEmpty() {
        }

        public LeftClickEmpty(Player player) {
            super(player, InteractionHand.MAIN_HAND, player.blockPosition(), Direction.UP);
        }
    }

    @Cancelable
    public static class RightClickBlock extends PlayerInteractEvent {

        public RightClickBlock() {
        }

        public RightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitResult) {
            super(player, hand, pos, hitResult.getDirection(), hitResult);
        }
    }
}
