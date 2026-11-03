package net.minecraftforge.common;

import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.IEventBus;

public final class MinecraftForge {

    public static final IEventBus EVENT_BUS = BusBuilder.builder().build();

    private MinecraftForge() {
    }
}
