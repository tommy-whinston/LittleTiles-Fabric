package net.minecraftforge.client.event;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

public class ModelEvent extends Event {

    public static class RegisterAdditional extends ModelEvent {

        public void register(ModelResourceLocation location) {
        }
    }

    public static class RegisterGeometryLoaders extends ModelEvent {

        public void register(ResourceLocation location, Object loader) {
        }
    }
}
