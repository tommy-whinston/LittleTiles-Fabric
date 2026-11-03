package team.creative.littletiles.common.block.little.tile.group;

import java.util.Collections;
import java.util.List;

import team.creative.littletiles.common.block.little.element.LittleElement;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.math.box.LittleBox;
import team.creative.littletiles.common.math.box.volume.LittleVolumes;
import team.creative.littletiles.common.placement.box.LittlePlaceBox;
import team.creative.littletiles.common.structure.LittleStructure;
import team.creative.littletiles.common.structure.LittleStructureType;

public class LittleGroupHolder extends LittleGroup {
    
    public final LittleStructure structure;
    
    public LittleGroupHolder(LittleStructure structure) {
        super();
        this.structure = structure;
    }
    
    public boolean hasStructure() {
        return true;
    }
    
    public String getStructureId() {
        return structure.type.id;
    }
    
    public String getStructureName() {
        return structure.name;
    }
    
    public int getSmallest() {
        return LittleGrid.MIN.count;
    }
    
    public LittleStructureType getStructureType() {
        return structure.type;
    }
    
    public boolean containsIngredients() {
        return true;
    }
    
    public LittleGroupHolder copy() {
        return new LittleGroupHolder(structure);
    }
    
    public boolean hasChildren() {
        return false;
    }
    
    public boolean transformable() {
        return false;
    }
    
    public double getVolume() {
        return 0;
    }
    
    public LittleVolumes getVolumes() {
        return null;
    }
    
    public List<LittlePlaceBox> getSpecialBoxes() {
        return Collections.EMPTY_LIST;
    }
    
    @Deprecated
    public void add(LittleGrid grid, LittleElement element, Iterable<LittleBox> boxes) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public void add(LittleGrid grid, LittleElement element, LittleBox box) {
        throw new UnsupportedOperationException();
    }
    
}
