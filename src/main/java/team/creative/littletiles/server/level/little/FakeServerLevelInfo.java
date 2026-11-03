package team.creative.littletiles.server.level.little;

import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.border.WorldBorder.Settings;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.timers.TimerQueue;
import team.creative.littletiles.common.level.little.FakeLevelInfo;

public class FakeServerLevelInfo extends FakeLevelInfo implements ServerLevelData {
    
    public FakeServerLevelInfo(Difficulty difficulty, boolean hardcore, boolean isFlat) {
        super(difficulty, hardcore, isFlat);
    }
    
    public String getLevelName() {
        return "fake";
    }
    
    public void setThundering(boolean thundering) {}
    
    public int getRainTime() {
        return 0;
    }
    
    public void setRainTime(int time) {}
    
    public void setThunderTime(int time) {}
    
    public int getThunderTime() {
        return 0;
    }
    
    public int getClearWeatherTime() {
        return 0;
    }
    
    public void setClearWeatherTime(int time) {}
    
    public int getWanderingTraderSpawnDelay() {
        return 0;
    }
    
    public void setWanderingTraderSpawnDelay(int delay) {}
    
    public int getWanderingTraderSpawnChance() {
        return 0;
    }
    
    public void setWanderingTraderSpawnChance(int chance) {}
    
    public UUID getWanderingTraderId() {
        return null;
    }
    
    public void setWanderingTraderId(UUID uuid) {}
    
    public GameType getGameType() {
        return GameType.SURVIVAL;
    }
    
    public void setWorldBorder(Settings settings) {}
    
    public Settings getWorldBorder() {
        return WorldBorder.DEFAULT_SETTINGS;
    }
    
    public boolean isInitialized() {
        return true;
    }
    
    public void setInitialized(boolean init) {}
    
    public boolean getAllowCommands() {
        return false;
    }
    
    public void setGameType(GameType type) {}
    
    public TimerQueue<MinecraftServer> getScheduledEvents() {
        return null;
    }
    
}
