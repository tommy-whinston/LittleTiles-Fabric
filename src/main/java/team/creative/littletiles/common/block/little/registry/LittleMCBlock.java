package team.creative.littletiles.common.block.little.registry;

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
import team.creative.littletiles.api.common.block.ILittleMCBlock;
import team.creative.littletiles.api.common.block.LittleBlock;
import team.creative.littletiles.client.render.tile.LittleRenderBox;
import team.creative.littletiles.common.block.little.element.LittleElement;
import team.creative.littletiles.common.block.little.tile.LittleTile;
import team.creative.littletiles.common.block.little.tile.parent.IParentCollection;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.math.box.LittleBox;
import team.creative.littletiles.common.math.vec.LittleVec;
import team.creative.littletiles.mixin.common.level.BlockBehaviourAccessor;

public class LittleMCBlock implements LittleBlock {
    
    public final Block block;
    private final boolean translucent;
    
    public LittleMCBlock(Block block) {
        this.block = block;
        this.translucent = ILittleMCBlock.isTranslucent(block);
    }
    
    public boolean is(ItemStack stack) {
        return Block.byItem(stack.getItem()) == block;
    }
    
    public ItemStack getStack() {
        return new ItemStack(block);
    }
    
    public boolean canBeRenderCombined(LittleTile one, LittleTile two) {
        return one.getBlock() == two.getBlock() && one.color == two.color;
    }
    
    public boolean noCollision() {
        return !((BlockBehaviourAccessor) block).getHasCollision();
    }
    
    public boolean is(Block block) {
        return this.block == block;
    }
    
    public boolean is(TagKey<Block> tag) {
        return block.builtInRegistryHolder().is(tag);
    }
    
    public boolean isTranslucent() {
        return translucent;
    }
    
    public boolean canBeConvertedToVanilla() {
        return true;
    }
    
    public BlockState mirror(BlockState state, Axis axis, LittleVec doubledCenter) {
        return state;
    }
    
    public BlockState rotate(BlockState state, Rotation rotation, LittleVec doubledCenter) {
        return state;
    }
    
    public SoundType getSoundType() {
        return block.getSoundType(getState());
    }
    
    public float getExplosionResistance(LittleTile tile) {
        return block.getExplosionResistance();
    }
    
    public void exploded(IParentCollection parent, LittleTile tile, Explosion explosion) {}
    
    public void randomDisplayTick(IParentCollection parent, LittleTile tile, RandomSource rand) {}
    
    public int getLightValue() {
        return getState().getLightEmission();
    }
    
    public BlockState getState() {
        return block.defaultBlockState();
    }
    
    public String blockName() {
        return block.builtInRegistryHolder().key().location().toString();
    }
    
    public boolean canInteract() {
        return false;
    }
    
    public InteractionResult use(IParentCollection parent, LittleTile tile, LittleBox box, Player player, BlockHitResult result, InteractionHand hand) {
        return InteractionResult.PASS;
    }
    
    public float getEnchantPowerBonus(IParentCollection parent, LittleTile tile) {
        float bonus = 0;
        if (bonus > 0)
            return (float) (bonus * tile.getPercentVolume(parent.getGrid()));
        return 0;
    }
    
    public float getFriction(IParentCollection parent, LittleTile tile, Entity entity) {
        return block.getFriction();
    }
    
    public boolean isFluid(TagKey<Fluid> fluid) {
        if (block instanceof LiquidBlock b)
            return b.getFluidState(b.defaultBlockState()).is(fluid);
        return false;
    }
    
    public LittleRenderBox getRenderBox(LittleGrid grid, RenderType layer, LittleBox box, LittleElement element) {
        return new LittleRenderBox(grid, box, element);
    }
    
    public boolean checkEntityCollision() {
        return false;
    }
    
    public void entityCollided(IParentCollection parent, LittleTile tile, Entity entity) {}
    
    public Vector3d getFogColor(IParentCollection parent, LittleTile tile, Entity entity, Vector3d originalColor, float partialTicks) {
        return tile.getFogColor(parent, entity, originalColor, partialTicks);
    }
    
    public boolean shouldUseStateForRenderType() {
        return true;
    }
}
