package team.creative.littletiles.common.placement.setting;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.creative.littletiles.LittleTiles;
import team.creative.littletiles.client.LittleTilesClient;
import team.creative.littletiles.common.grid.LittleGrid;
import team.creative.littletiles.common.packet.action.PlacementPlayerSettingPacket;
import team.creative.littletiles.common.placement.mode.PlacementMode;

public class PlacementPlayerSetting {
    
    public static final String SETTING_KEY = "littletiles:place";
    private static final Map<UUID, PlacementPlayerSetting> SERVER_SETTINGS = new ConcurrentHashMap<>();
    
    public static void setServer(Player player, PlacementPlayerSetting setting) {
        if (player != null && setting != null)
            SERVER_SETTINGS.put(player.getUUID(), setting);
    }
    
    public static PlacementPlayerSetting getServer(Player player) {
        return player == null ? null : SERVER_SETTINGS.get(player.getUUID());
    }
    
    @OnlyIn(Dist.CLIENT)
    private static LittleGrid gridClient() {
        if (LittleTilesClient.ACTION_HANDLER == null || LittleTilesClient.ACTION_HANDLER.setting == null)
            return LittleGrid.overallDefault();
        LittleGrid grid = LittleTilesClient.ACTION_HANDLER.setting.grid();
        return grid != null ? grid : LittleGrid.overallDefault();
    }
    
    public static LittleGrid grid(Player player) {
        if (player == null)
            return LittleGrid.overallDefault();
        if (player.level().isClientSide)
            return gridClient();
        PlacementPlayerSetting setting = getServer(player);
        if (setting != null && setting.grid != null)
            return setting.grid;
        CompoundTag nbt = new CompoundTag();
        return LittleTiles.CONFIG.build.get(player).getOrDefault(nbt.contains(LittleGrid.GRID_KEY) ? nbt.getInt(LittleGrid.GRID_KEY) : -1);
    }
    
    @OnlyIn(Dist.CLIENT)
    private static PlacementMode placementModeClient() {
        if (LittleTilesClient.ACTION_HANDLER == null || LittleTilesClient.ACTION_HANDLER.setting == null)
            return PlacementMode.getDefault();
        return LittleTilesClient.ACTION_HANDLER.setting.placementMode();
    }
    
    public static PlacementMode placementMode(Player player) {
        if (player.level().isClientSide)
            return placementModeClient();
        PlacementPlayerSetting setting = getServer(player);
        if (setting != null && setting.mode != null)
            return setting.mode;
        return PlacementMode.getDefault();
    }
    
    protected PlacementMode mode;
    protected LittleGrid grid;
    private int configuredGrid;
    
    public PlacementPlayerSetting() {
        mode = PlacementMode.getDefault();
        configuredGrid = -1;
        grid = LittleGrid.overallDefault();
    }
    
    public PlacementPlayerSetting(CompoundTag nbt) {
        mode = PlacementMode.getMode(nbt.getString("mode"));
        configuredGrid = nbt.contains(LittleGrid.GRID_KEY) ? nbt.getInt(LittleGrid.GRID_KEY) : -1;
        grid = LittleGrid.overallDefault();
    }
    
    public CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(LittleGrid.GRID_KEY, configuredGrid);
        nbt.putString("mode", mode.getId());
        return nbt;
    }
    
    public void receive(Player player, PlacementPlayerSetting settings) {
        this.configuredGrid = settings.configuredGrid;
        this.grid = settings.grid;
        this.mode = settings.mode;
        refreshGrid(player);
    }
    
    public LittleGrid grid() {
        return grid;
    }
    
    public PlacementMode placementMode() {
        return mode;
    }
    
    public void changed() {
        LittleTiles.NETWORK.sendToServer(new PlacementPlayerSettingPacket(this));
    }
    
    public void grid(LittleGrid grid) {
        this.grid = grid;
        this.configuredGrid = grid.count;
        changed();
    }
    
    public void placementMode(PlacementMode mode) {
        this.mode = mode;
        changed();
    }
    
    public void set(LittleGrid grid, PlacementMode mode) {
        this.grid = grid;
        this.configuredGrid = grid.count;
        this.mode = mode;
        changed();
    }
    
    public void refreshGrid(Player player) {
        if (player != null && LittleTiles.CONFIG != null)
            grid = LittleTiles.CONFIG.build.get(player).getOrDefault(configuredGrid);
        if (grid == null)
            grid = LittleGrid.overallDefault();
    }
    
}
