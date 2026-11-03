package team.creative.littletiles.common.placement.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;

public class PlacementModeAll extends PlacementModeNormal {
    
    public PlacementModeAll(PreviewMode mode) {
        super(mode, false);
    }
    
    public boolean canPlaceStructures() {
        return true;
    }
    
    public PlacementMode place() {
        if (Screen.hasControlDown())
            return PlacementMode.OVERWRITE;
        return super.place();
    }
    
    public List<BlockPos> getCoordsToCheck(Set<BlockPos> splittedTiles, BlockPos pos) {
        return new ArrayList<>(splittedTiles);
    }
}
