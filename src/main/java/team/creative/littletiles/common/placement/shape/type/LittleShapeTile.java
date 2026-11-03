package team.creative.littletiles.common.placement.shape.type;

import java.util.Collections;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.transformation.Rotation;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.math.box.collection.LittleBoxes;
import team.creative.littletiles.common.placement.shape.ShapeSelection;
import team.creative.littletiles.common.placement.shape.ShapeSelection.ShapeSelectPos;

public class LittleShapeTile extends LittleShapeSelectable {
    
    public LittleShapeTile() {
        super(1);
    }
    
    protected void addBoxes(LittleBoxes boxes, ShapeSelection selection, boolean lowResolution) {
        for (ShapeSelectPos pos : selection) {
            if (pos.result.isComplete())
                addBox(boxes, selection.inside, selection.getGrid(), pos.result.parent, pos.result.box, pos.pos.facing);
            else
                addBox(boxes, selection.inside, selection.getGrid(), pos.ray.getBlockPos(), pos.pos.facing);
        }
    }
    
    public void addExtraInformation(CompoundTag nbt, List<Component> list) {}
    
    @OnlyIn(Dist.CLIENT)
    public List<GuiControl> getCustomSettings(CompoundTag nbt, LittleGrid grid) {
        return Collections.EMPTY_LIST;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void saveCustomSettings(GuiParent gui, CompoundTag nbt, LittleGrid grid) {}
    
    public void rotate(CompoundTag nbt, Rotation rotation) {}
    
    public void mirror(CompoundTag nbt, Axis axis) {}
    
}
