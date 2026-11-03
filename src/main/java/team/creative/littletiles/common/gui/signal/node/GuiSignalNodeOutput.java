package team.creative.littletiles.common.gui.signal.node;

import java.util.Iterator;
import java.util.List;

import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.util.type.itr.IterableIterator;
import team.creative.littletiles.common.gui.signal.GeneratePatternException;
import team.creative.littletiles.common.gui.signal.GuiSignalComponent;
import team.creative.littletiles.common.gui.signal.GuiSignalConnection;
import team.creative.littletiles.common.structure.signal.input.SignalInputCondition;

public class GuiSignalNodeOutput extends GuiSignalNodeComponent {
    
    public GuiSignalConnection from;
    
    public GuiSignalNodeOutput(GuiSignalComponent component) {
        super(component);
        setTitle(Component.translatable("gui.signal.out").append(": " + component.name()));
    }
    
    public boolean canConnectTo(GuiSignalNode node) {
        return false;
    }
    
    public boolean canConnectFrom(GuiSignalNode node) {
        return from == null;
    }
    
    public Iterator<GuiSignalConnection> iterator() {
        return new Iterator<GuiSignalConnection>() {
            
            public boolean has = from != null;
            
            public boolean hasNext() {
                return has;
            }
            
            public GuiSignalConnection next() {
                has = false;
                return from;
            }
        };
    }
    
    public Iterable<GuiSignalConnection> toConnections() {
        return new IterableIterator<GuiSignalConnection>() {
            
            public boolean hasNext() {
                return false;
            }
            
            public GuiSignalConnection next() {
                return null;
            }
            
        };
    }
    
    public GuiSignalConnection getConnectionTo(GuiSignalNode node) {
        return null;
    }
    
    public void disconnect(GuiSignalConnection connection) {
        from = null;
    }
    
    public void connect(GuiSignalConnection connection) {
        from = connection;
    }
    
    public void remove() {
        if (from != null)
            from.disconnect(controller());
    }
    
    public int indexOf(GuiSignalConnection connection) {
        return 0;
    }
    
    public SignalInputCondition generateCondition(List<GuiSignalNode> processed) throws GeneratePatternException {
        reset();
        if (from == null)
            throw new GeneratePatternException(this, "empty");
        return from.from().generateCondition(processed);
    }
    
    public boolean removable() {
        return false;
    }
    
}
