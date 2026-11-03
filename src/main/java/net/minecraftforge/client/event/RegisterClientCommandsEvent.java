package net.minecraftforge.client.event;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.eventbus.api.Event;

public class RegisterClientCommandsEvent extends Event {

    private final CommandDispatcher<CommandSourceStack> dispatcher;

    public RegisterClientCommandsEvent() {
        this.dispatcher = null;
    }

    public RegisterClientCommandsEvent(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return dispatcher;
    }
}
