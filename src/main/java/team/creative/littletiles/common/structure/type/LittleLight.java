package team.creative.littletiles.common.structure.type;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import team.creative.littletiles.common.block.little.tile.LittleTileContext;
import team.creative.littletiles.common.block.little.tile.parent.IStructureParentCollection;
import team.creative.littletiles.common.structure.LittleStructure;
import team.creative.littletiles.common.structure.LittleStructureType;
import team.creative.littletiles.common.structure.attribute.LittleStructureAttribute;
import team.creative.littletiles.common.structure.exception.CorruptedConnectionException;
import team.creative.littletiles.common.structure.exception.NotYetConnectedException;
import team.creative.littletiles.common.structure.signal.output.InternalSignalOutput;

public class LittleLight extends LittleStructure {
    
    public int level;
    public boolean allowRightClick = true;
    
    public LittleLight(LittleStructureType type, IStructureParentCollection mainBlock) {
        super(type, mainBlock);
    }
    
    protected void loadExtra(CompoundTag nbt) {
        level = nbt.getInt("level");
        allowRightClick = nbt.getBoolean("right");
    }
    
    protected void saveExtra(CompoundTag nbt) {
        nbt.putInt("level", level);
        nbt.putBoolean("right", allowRightClick);
    }
    
    public int getLightValue(BlockPos pos) {
        return getOutput(0).getState().any() ? level : 0;
    }
    
    public boolean canInteract() {
        return allowRightClick;
    }
    
    public InteractionResult use(Level level, LittleTileContext context, BlockPos pos, Player player, BlockHitResult result, InteractionHand hand) {
        if (!level.isClientSide && allowRightClick)
            getOutput(0).toggle();
        return InteractionResult.SUCCESS;
    }
    
    public void performInternalOutputChange(InternalSignalOutput output) {
        if (output.component.is("enabled"))
            try {
                tryAttributeChangeForBlocks();
            } catch (CorruptedConnectionException | NotYetConnectedException e) {}
    }
    
    public int getAttribute() {
        if (getOutput(0).getState().any())
            return super.getAttribute() | LittleStructureAttribute.EMISSIVE;
        return super.getAttribute();
    }
    
}
