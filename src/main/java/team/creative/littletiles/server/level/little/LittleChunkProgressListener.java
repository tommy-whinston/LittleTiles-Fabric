package team.creative.littletiles.server.level.little;

import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public class LittleChunkProgressListener implements ChunkProgressListener {
    
    public static final LittleChunkProgressListener INSTANCE = new LittleChunkProgressListener();
    
    public void updateSpawnPos(ChunkPos pos) {}
    
    public void onStatusChange(ChunkPos pos, ChunkStatus status) {}
    
    public void start() {}
    
    public void stop() {}
    
}
