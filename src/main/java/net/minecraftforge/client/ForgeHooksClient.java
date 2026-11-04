package net.minecraftforge.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;

public final class ForgeHooksClient {

    private ForgeHooksClient() {
    }

    public static float getGuiFarPlane() {
        return 1000.0F;
    }

    public static InputEvent.InteractionKeyMappingTriggered onClickInput(int button, KeyMapping keyMapping, InteractionHand hand) {
        InputEvent.InteractionKeyMappingTriggered event = new InputEvent.InteractionKeyMappingTriggered(button, keyMapping, hand);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
