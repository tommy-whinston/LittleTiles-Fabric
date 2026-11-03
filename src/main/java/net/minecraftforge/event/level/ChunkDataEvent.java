package net.minecraftforge.event.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraftforge.eventbus.api.Event;

public class ChunkDataEvent extends Event {

    private final LevelChunk chunk;
    private final CompoundTag data;

    public ChunkDataEvent() {
        this.chunk = null;
        this.data = new CompoundTag();
    }

    public ChunkDataEvent(LevelChunk chunk, CompoundTag data) {
        this.chunk = chunk;
        this.data = data;
    }

    public LevelChunk getChunk() {
        return chunk;
    }

    public Level getLevel() {
        return (Level) chunk.getLevel();
    }

    public CompoundTag getData() {
        return data;
    }

    public static class Load extends ChunkDataEvent {

        private final ChunkStatus.ChunkType type;

        public Load() {
            this.type = null;
        }

        public Load(LevelChunk chunk, CompoundTag data, ChunkStatus.ChunkType type) {
            super(chunk, data);
            this.type = type;
        }

        public ChunkStatus.ChunkType getType() {
            return type;
        }
    }

    public static class Save extends ChunkDataEvent {

        public Save() {
        }

        public Save(LevelChunk chunk, Level level, CompoundTag data) {
            super(chunk, data);
        }
    }
}
