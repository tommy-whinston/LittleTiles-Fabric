package net.minecraftforge.client.event;

import net.minecraft.client.KeyMapping;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.Event;

public class InputEvent extends Event {

    public static class InteractionKeyMappingTriggered extends InputEvent {

        private final int button;
        private final KeyMapping keyMapping;
        private final InteractionHand hand;
        private boolean swingHand = true;

        public InteractionKeyMappingTriggered(int button, KeyMapping keyMapping, InteractionHand hand) {
            this.button = button;
            this.keyMapping = keyMapping;
            this.hand = hand;
        }

        public boolean isPickBlock() {
            return button == 2;
        }

        public boolean shouldSwingHand() {
            return swingHand;
        }

        public void setSwingHand(boolean swingHand) {
            this.swingHand = swingHand;
        }

        public KeyMapping getKeyMapping() {
            return keyMapping;
        }

        public InteractionHand getHand() {
            return hand;
        }
    }
}
