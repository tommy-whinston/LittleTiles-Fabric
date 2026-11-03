package team.creative.littletiles.server.level.little;

import java.util.concurrent.Executor;

import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;

public class LittleDistanceManager extends DistanceManager {
    
    protected LittleDistanceManager(Executor exe, Executor exe2) {
        super(exe, exe2);
    }
    
    protected boolean isChunkToRemove(long pos) {
        return false;
    }
    
    protected ChunkHolder getChunk(long pos) {
        return null;
    }
    
    protected ChunkHolder updateChunkScheduling(long pos, int oldLevel, ChunkHolder holder, int newLevel) {
        return null;
    }
    
    public boolean runAllUpdates(ChunkMap map) {
        return true;
    }
    
    public <T> void addTicket(TicketType<T> type, ChunkPos pos, int level, T key) {}
    
    public <T> void removeTicket(TicketType<T> type, ChunkPos pos, int level, T key) {}
    
    public <T> void addRegionTicket(TicketType<T> type, ChunkPos pos, int level, T key) {}
    
    public <T> void addRegionTicket(TicketType<T> type, ChunkPos pos, int level, T key, boolean forceTicks) {}
    
    public <T> void removeRegionTicket(TicketType<T> type, ChunkPos pos, int level, T key) {}
    
    public <T> void removeRegionTicket(TicketType<T> type, ChunkPos pos, int level, T key, boolean forceTicks) {}
    
    public void addPlayer(SectionPos pos, ServerPlayer player) {}
    
    public void removePlayer(SectionPos pos, ServerPlayer player) {
        
    }
    
    public boolean inEntityTickingRange(long pos) {
        return true;
    }
    
    public boolean inBlockTickingRange(long pos) {
        return true;
    }
    
    public void updateSimulationDistance(int distance) {}
    
    public int getNaturalSpawnChunkCount() {
        return 0;
    }
    
    public boolean hasPlayersNearby(long pos) {
        return false;
    }
    
    public String getDebugStatus() {
        return "";
    }
    
    public boolean shouldForceTicks(long chunkPos) {
        return false;
    }
    
    public void removeTicketsOnClosing() {}
    
    public boolean hasTickets() {
        return false;
    }
    
    public void purgeStaleTickets() {}
    
    public void updateChunkForced(ChunkPos pos, boolean added) {}
}
