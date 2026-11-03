package net.minecraftforge.common.capabilities;

import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public final class ForgeCapabilities {

    private ForgeCapabilities() {
    }

    public static final Capability<IItemHandler> ITEM_HANDLER = new Capability<>(IItemHandler.class);
    public static final Capability<IFluidHandler> FLUID_HANDLER = new Capability<>(IFluidHandler.class);
}
