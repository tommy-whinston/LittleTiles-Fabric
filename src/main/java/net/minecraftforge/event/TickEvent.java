package net.minecraftforge.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

public class TickEvent extends Event {

    public enum Phase {
        START,
        END
    }

    public static class ClientTickEvent extends TickEvent {

        public ClientTickEvent() {
        }

        public ClientTickEvent(Phase phase) {
            super(phase);
        }
    }

    public static class ServerTickEvent extends TickEvent {

        public ServerTickEvent() {
        }

        public ServerTickEvent(Phase phase) {
            super(phase);
        }
    }

    public static class RenderTickEvent extends TickEvent {

        public RenderTickEvent() {
        }

        public RenderTickEvent(Phase phase) {
            super(phase);
        }
    }

    public static class LevelTickEvent extends TickEvent {

        public final Level level;

        public LevelTickEvent() {
            this.level = null;
        }

        public LevelTickEvent(Phase phase, Level level) {
            super(phase);
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    public final Phase phase;

    public TickEvent() {
        this.phase = null;
    }

    public TickEvent(Phase phase) {
        this.phase = phase;
    }
}
