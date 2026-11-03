package net.minecraftforge.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.eventbus.api.Event;

public class RenderGuiEvent extends Event {

    private final GuiGraphics guiGraphics;
    private final float partialTick;

    public RenderGuiEvent(GuiGraphics guiGraphics, float partialTick) {
        this.guiGraphics = guiGraphics;
        this.partialTick = partialTick;
    }

    public GuiGraphics getGuiGraphics() {
        return guiGraphics;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public static class Post extends RenderGuiEvent {

        public Post(GuiGraphics guiGraphics, float partialTick) {
            super(guiGraphics, partialTick);
        }
    }
}
