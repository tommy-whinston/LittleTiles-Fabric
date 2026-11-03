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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.transformation.Rotation;
import team.creative.littletiles.api.common.block.LittleBlock;
import team.creative.littletiles.client.render.tile.LittleRenderBox;
import team.creative.littletiles.common.block.little.element.LittleElement;
import team.creative.littletiles.common.block.little.tile.LittleTile;
import team.creative.littletiles.common.block.little.tile.parent.IParentCollection;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.math.box.LittleBox;
import team.creative.littletiles.common.math.vec.LittleVec;

public class LittleMissingBlock implements LittleBlock {
    
    public final String blockName;
    
    public LittleMissingBlock(String name) {
        this.blockName = name;
    }
    
    public String blockName() {
        return blockName;
    }
    
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }
    
    public boolean isTranslucent() {
        return false;
    }
    
    public boolean is(Block block) {
        return false;
    }
    
    public boolean is(TagKey<Block> tag) {
        return false;
    }
    
    public BlockState getState() {
        return Blocks.AIR.defaultBlockState();
    }
    
    public boolean canBeConvertedToVanilla() {
        return false;
    }
    
    public BlockState mirror(BlockState state, Axis axis, LittleVec doubledCenter) {
        return state;
    }
    
    public BlockState rotate(BlockState state, Rotation rotation, LittleVec doubledCenter) {
        return state;
    }
    
    public SoundType getSoundType() {
        return SoundType.STONE;
    }
    
    public float getExplosionResistance(LittleTile tile) {
        return 0;
    }
    
    public void exploded(IParentCollection parent, LittleTile tile, Explosion explosion) {}
    
    public void randomDisplayTick(IParentCollection parent, LittleTile tile, RandomSource rand) {
        
    }
    
    public boolean canInteract() {
        return false;
    }
    
    public InteractionResult use(IParentCollection parent, LittleTile tile, LittleBox box, Player player, BlockHitResult result, InteractionHand hand) {
        return InteractionResult.PASS;
    }
    
    public int getLightValue() {
        return 0;
    }
    
    public float getEnchantPowerBonus(IParentCollection parent, LittleTile tile) {
        return 0;
    }
    
    public float getFriction(IParentCollection parent, LittleTile tile, Entity entity) {
        return 0;
    }
    
    public boolean isFluid(TagKey<Fluid> fluid) {
        return false;
    }
    
    public Vector3d getFogColor(IParentCollection parent, LittleTile tile, Entity entity, Vector3d originalColor, float partialTicks) {
        return IClientBlockExtensions.of(tile.getState()).getFogColor(getState(), parent.getLevel(), parent.getPos(), entity, originalColor, partialTicks);
    }
    
    public LittleRenderBox getRenderBox(LittleGrid grid, RenderType layer, LittleBox box, LittleElement element) {
        return new LittleRenderBox(grid, box, element);
    }
    
    public boolean canBeRenderCombined(LittleTile one, LittleTile two) {
        return two.getBlock() instanceof LittleMissingBlock;
    }
    
    public boolean checkEntityCollision() {
        return false;
    }
    
    public void entityCollided(IParentCollection parent, LittleTile tile, Entity entity) {}
    
    public boolean is(ItemStack stack) {
        return false;
    }
    
    public boolean noCollision() {
        return false;
    }
    
    public boolean shouldUseStateForRenderType() {
        return false;
    }
}
