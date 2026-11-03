package team.creative.littletiles.common.block.little.tile.parent;

import java.util.Iterator;

import net.minecraft.nbt.CompoundTag;
import team.creative.creativecore.common.util.filter.BiFilter;
import team.creative.creativecore.common.util.type.itr.IterableIterator;
import team.creative.littletiles.common.block.entity.BETiles;
import team.creative.littletiles.common.block.little.element.LittleElement;
import team.creative.littletiles.common.block.little.tile.LittleTile;
import team.creative.littletiles.common.block.little.tile.collection.LittleCollection;
import team.creative.littletiles.common.block.little.tile.collection.LittleCollectionSafe;
import team.creative.littletiles.common.math.face.LittleServerFace;
import team.creative.littletiles.common.structure.LittleStructure;
import team.creative.littletiles.common.structure.exception.CorruptedConnectionException;
import team.creative.littletiles.common.structure.exception.NotYetConnectedException;

public abstract class ParentCollection extends LittleCollectionSafe implements IParentCollection {
    
    private int collisionChecks = 0;
    
    protected boolean checkCollision() {
        return collisionChecks > 0;
    }
    
    protected void added(LittleElement tile) {
        if (tile.checkEntityCollision())
            collisionChecks++;
    }
    
    protected void refresh() {
        collisionChecks = 0;
        for (LittleTile tile : this)
            added(tile);
    }
    
    protected void removed(LittleElement tile) {
        if (tile.checkEntityCollision())
            collisionChecks--;
    }
    
    public void load(CompoundTag nbt) {
        this.clear();
        LittleCollection.loadExtended(this, nbt.getCompound("tiles"));
        loadExtra(nbt);
    }
    
    protected abstract void loadExtra(CompoundTag nbt);
    
    public CompoundTag save(LittleServerFace face) {
        CompoundTag nbt = new CompoundTag();
        nbt.put("tiles", LittleCollection.saveExtended(this, face));
        saveExtra(nbt, face);
        return nbt;
    }
    
    protected abstract void saveExtra(CompoundTag nbt, LittleServerFace face);
    
    public Iterable<LittleTile> filter(BiFilter<IParentCollection, LittleTile> selector) {
        return new IterableIterator<LittleTile>() {
            
            Iterator<LittleTile> itr = content.iterator();
            LittleTile next = null;
            
            public boolean hasNext() {
                while (next == null && itr.hasNext()) {
                    LittleTile test = itr.next();
                    if (selector.is(ParentCollection.this, test))
                        next = test;
                }
                return next != null;
            }
            
            public LittleTile next() {
                LittleTile result = next;
                next = null;
                return result;
            }
            
        };
    }
    
    public abstract BETiles getBE();
    
    public abstract boolean isStructure();
    
    public boolean isStructureChildSafe(LittleStructure structure) {
        try {
            return isStructureChild(structure);
        } catch (CorruptedConnectionException | NotYetConnectedException e) {
            return false;
        }
    }
    
    public abstract boolean isStructureChild(LittleStructure structure) throws CorruptedConnectionException, NotYetConnectedException;
    
    public abstract boolean isMain();
    
    public abstract LittleStructure getStructure() throws CorruptedConnectionException, NotYetConnectedException;
    
    public abstract int getAttribute();
    
    public abstract boolean isClient();
    
    public void unload() {}
}
