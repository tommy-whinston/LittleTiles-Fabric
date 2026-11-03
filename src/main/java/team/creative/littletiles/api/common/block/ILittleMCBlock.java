package team.creative.littletiles.api.common.block;

import org.joml.Vector3d;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.transformation.Rotation;
import team.creative.littletiles.client.render.tile.LittleRenderBox;
import team.creative.littletiles.common.block.little.element.LittleElement;
import team.creative.littletiles.common.block.little.tile.LittleTile;
import team.creative.littletiles.common.block.little.tile.parent.IParentCollection;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.math.box.LittleBox;
import team.creative.littletiles.common.math.vec.LittleVec;
import team.creative.littletiles.mixin.common.level.BlockBehaviourAccessor;

public interface ILittleMCBlock extends LittleBlock {
    
    static final RandomSource RANDOM = RandomSource.create();
    
    public static boolean isTranslucent(Block block) {
        return !block.defaultBlockState().isSolid() || !block.defaultBlockState().canOcclude(); // Also depends on block model which is not considered at the moment
    }
    
    public Block asVanillaBlock();
    
    public default boolean isTranslucent() {
        return isTranslucent(asVanillaBlock());
    }
    
    public default boolean is(ItemStack stack) {
        return Block.byItem(stack.getItem()) == asVanillaBlock();
    }
    
    public default boolean is(TagKey<Block> tag) {
        return asVanillaBlock().builtInRegistryHolder().is(tag);
    }
    
    public default ItemStack getStack() {
        return new ItemStack(asVanillaBlock());
    }
    
    public default boolean canBeRenderCombined(LittleTile one, LittleTile two) {
        return one.getBlock() == two.getBlock() && one.color == two.color;
    }
    
    public default boolean noCollision() {
        return !((BlockBehaviourAccessor) asVanillaBlock()).getHasCollision();
    }
    
    public default boolean is(Block block) {
        return this.asVanillaBlock() == block;
    }
    
    public default boolean canBeConvertedToVanilla() {
        return true;
    }
    
    public default BlockState mirror(BlockState state, Axis axis, LittleVec doubledCenter) {
        return state;
    }
    
    public default BlockState rotate(BlockState state, Rotation rotation, LittleVec doubledCenter) {
        return state;
    }
    
    public default SoundType getSoundType() {
        return asVanillaBlock().getSoundType(getState());
    }
    
    public default float getExplosionResistance(LittleTile tile) {
        return asVanillaBlock().getExplosionResistance();
    }
    
    public default void exploded(IParentCollection parent, LittleTile tile, Explosion explosion) {}
    
    public default void randomDisplayTick(IParentCollection parent, LittleTile tile, RandomSource rand) {}
    
    public default int getLightValue() {
        return getState().getLightEmission();
    }
    
    public default BlockState getState() {
        return asVanillaBlock().defaultBlockState();
    }
    
    public default String blockName() {
        return asVanillaBlock().builtInRegistryHolder().key().location().toString();
    }
    
    public default boolean canInteract() {
        return false;
    }
    
    public default InteractionResult use(IParentCollection parent, LittleTile tile, LittleBox box, Player player, BlockHitResult result, InteractionHand hand) {
        return InteractionResult.PASS;
    }
    
    public default float getEnchantPowerBonus(IParentCollection parent, LittleTile tile) {
        float bonus = 0;
        if (bonus > 0)
            return (float) (bonus * tile.getPercentVolume(parent.getGrid()));
        return 0;
    }
    
    public default float getFriction(IParentCollection parent, LittleTile tile, Entity entity) {
        return asVanillaBlock().getFriction();
    }
    
    public default boolean isFluid(TagKey<Fluid> fluid) {
        if (asVanillaBlock() instanceof LiquidBlock b)
            return b.getFluidState(b.defaultBlockState()).is(fluid);
        return false;
    }
    
    public default LittleRenderBox getRenderBox(LittleGrid grid, RenderType layer, LittleBox box, LittleElement element) {
        return new LittleRenderBox(grid, box, element);
    }
    
    public default boolean checkEntityCollision() {
        return false;
    }
    
    public default void entityCollided(IParentCollection parent, LittleTile tile, Entity entity) {}
    
    public default Vector3d getFogColor(IParentCollection parent, LittleTile tile, Entity entity, Vector3d originalColor, float partialTicks) {
        return originalColor;
    }
    
    public default boolean shouldUseStateForRenderType() {
        return true;
    }
    
}
