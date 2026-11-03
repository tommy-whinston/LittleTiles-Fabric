package team.creative.littletiles.common.ingredient;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

/** Minimal Fabric-side replacement for Forge's player item handler capability. */
public class PlayerItemHandler implements IItemHandler {
    
    private final Inventory inventory;
    
    public PlayerItemHandler(Inventory inventory) {
        this.inventory = inventory;
    }
    
    @Override
    public int getSlots() {
        return 36;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getItem(slot);
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        
        ItemStack existing = inventory.getItem(slot);
        int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameTags(existing, stack))
                return stack;
            int amount = Math.min(limit - existing.getCount(), stack.getCount());
            if (amount <= 0)
                return stack;
            if (!simulate) {
                existing.grow(amount);
                inventory.setChanged();
            }
            ItemStack result = stack.copy();
            result.shrink(amount);
            return result;
        }
        
        int amount = Math.min(limit, stack.getCount());
        if (!simulate) {
            ItemStack copy = stack.copy();
            copy.setCount(amount);
            inventory.setItem(slot, copy);
            inventory.setChanged();
        }
        ItemStack result = stack.copy();
        result.shrink(amount);
        return result;
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0)
            return ItemStack.EMPTY;
        
        ItemStack existing = inventory.getItem(slot);
        if (existing.isEmpty())
            return ItemStack.EMPTY;
        
        int count = Math.min(amount, existing.getCount());
        ItemStack result = existing.copy();
        result.setCount(count);
        if (!simulate) {
            existing.shrink(count);
            if (existing.isEmpty())
                inventory.setItem(slot, ItemStack.EMPTY);
            inventory.setChanged();
        }
        return result;
    }
    
    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
    
    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }
}
