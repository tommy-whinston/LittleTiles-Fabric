package team.creative.littletiles.common.placement.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.core.BlockPos;
import team.creative.littletiles.common.action.LittleActionException;
import team.creative.littletiles.common.block.little.tile.LittleTile;
import team.creative.littletiles.common.placement.PlacementContext;
import team.creative.littletiles.common.structure.LittleStructure;

public class PlacementModeOverwriteAll extends PlacementModeAll {
    
    public PlacementModeOverwriteAll(PreviewMode mode) {
        super(mode);
    }
    
    public boolean shouldConvertBlock() {
        return true;
    }
    
    public boolean canPlaceStructures() {
        return true;
    }
    
    public boolean checkAll() {
        return false;
    }
    
    public List<BlockPos> getCoordsToCheck(Set<BlockPos> splittedTiles, BlockPos pos) {
        return new ArrayList<>(splittedTiles);
    }
    
    public void prepareBlock(PlacementContext context) {
        for (LittleTile toRemove : context.getParent())
            context.addRemoved(toRemove);
        context.getParent().clear();
    }
    
    public boolean placeTile(PlacementContext context, LittleStructure structure, LittleTile tile) throws LittleActionException {
        context.placeTile(tile);
        return true;
    }
    
}
