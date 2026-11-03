package team.creative.littletiles.common.gui.signal.node;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.littletiles.LittleTilesGuiRegistry;
import team.creative.littletiles.common.gui.signal.GeneratePatternException;
import team.creative.littletiles.common.gui.signal.GuiSignalConnection;
import team.creative.littletiles.common.structure.signal.input.SignalInputCondition;
import team.creative.littletiles.common.structure.signal.input.SignalInputCondition.SignalInputVirtualVariable;

public class GuiSignalNodeVirtualInput extends GuiSignalNode {
    
    public List<GuiSignalConnection> tos = new ArrayList<>();
    public SignalInputCondition[] conditions;
    
    public GuiSignalNodeVirtualInput() {
        super("v[]");
        this.conditions = new SignalInputCondition[0];
    }
    
    public GuiSignalNodeVirtualInput(SignalInputVirtualVariable variable) throws ParseException {
        super("v[]");
        this.conditions = variable.conditions;
        updateLabel();
    }
    
    public void updateLabel() {
        String conditionsText = "";
        for (int i = 0; i < conditions.length; i++) {
            if (i > 0)
                conditionsText += ",";
            conditionsText += conditions[i].write();
        }
        if (conditionsText.length() > 10)
            conditionsText = "...";
        setTitle(Component.literal("v[" + conditionsText + "]"));
        raiseEvent(new GuiControlChangedEvent(controller()));
    }
    
    public boolean mouseDoubleClicked(Rect rect, double x, double y, int button) {
        LittleTilesGuiRegistry.VIRTUAL_INPUT_DIALOG.open(getIntegratedParent(), new CompoundTag()).init(controller().inputs, this);
        return true;
    }
    
    public boolean canConnectTo(GuiSignalNode node) {
        for (GuiSignalConnection connectTo : tos)
            if (connectTo.to() == node)
                return false;
        return true;
    }
    
    public boolean canConnectFrom(GuiSignalNode node) {
        return false;
    }
    
    public GuiSignalConnection getConnectionTo(GuiSignalNode node) {
        for (GuiSignalConnection connectTo : tos)
            if (connectTo.to() == node)
                return connectTo;
        return null;
    }
    
    public void disconnect(GuiSignalConnection connection) {
        tos.remove(connection);
    }
    
    public void connect(GuiSignalConnection connection) {
        tos.add(connection);
    }
    
    public Iterator<GuiSignalConnection> iterator() {
        return tos.iterator();
    }
    
    public Iterable<GuiSignalConnection> toConnections() {
        return tos;
    }
    
    public void remove() {
        for (GuiSignalConnection connection : new ArrayList<>(tos))
            connection.disconnect(controller());
    }
    
    public int indexOf(GuiSignalConnection connection) {
        return tos.indexOf(connection);
    }
    
    public SignalInputCondition generateCondition(List<GuiSignalNode> processed) throws GeneratePatternException {
        reset();
        return new SignalInputVirtualVariable(conditions);
    }
    
}
