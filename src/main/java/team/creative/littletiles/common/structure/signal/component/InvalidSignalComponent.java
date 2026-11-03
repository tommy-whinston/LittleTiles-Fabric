package team.creative.littletiles.common.structure.signal.component;

import java.util.Iterator;

import net.minecraft.world.level.Level;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.structure.signal.network.SignalNetwork;

public class InvalidSignalComponent implements ISignalStructureBase {
    
    public static final InvalidSignalComponent INSTANCE = new InvalidSignalComponent();
    
    private InvalidSignalComponent() {}
    
    public Level getStructureLevel() {
        return null;
    }
    
    public int getBandwidth() {
        return 0;
    }
    
    public SignalNetwork getNetwork() {
        return null;
    }
    
    public void setNetwork(SignalNetwork network) {}
    
    public Iterator<ISignalStructureBase> connections() {
        return null;
    }
    
    public boolean canConnect(Facing facing) {
        return false;
    }
    
    public boolean connect(Facing facing, ISignalStructureBase base, LittleGrid grid, int distance, boolean oneSidedRenderer) {
        return false;
    }
    
    public void disconnect(Facing facing, ISignalStructureBase base) {}
    
    public SignalComponentType getComponentType() {
        return SignalComponentType.INVALID;
    }
    
    public int getColor() {
        return 0;
    }
    
    public void unload(Facing facing, ISignalStructureBase base) {}
    
}
