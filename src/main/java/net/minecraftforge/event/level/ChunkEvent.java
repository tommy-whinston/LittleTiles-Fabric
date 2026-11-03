package net.minecraftforge.event.level;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.eventbus.api.Event;

public class ChunkEvent extends Event {

    private final LevelChunk chunk;
    private final boolean newChunk;

    public ChunkEvent(LevelChunk chunk, boolean newChunk) {
        this.chunk = chunk;
        this.newChunk = newChunk;
    }

    public LevelChunk getChunk() {
        return chunk;
    }

    public LevelAccessor getLevel() {
        return chunk.getLevel();
    }

    public boolean isNewChunk() {
        return newChunk;
    }

    public static class Load extends ChunkEvent {

        public Load(LevelChunk chunk, boolean newChunk) {
            super(chunk, newChunk);
        }
    }

    public static class Unload extends ChunkEvent {

        public Unload(LevelChunk chunk) {
            super(chunk, false);
        }
    }
}
