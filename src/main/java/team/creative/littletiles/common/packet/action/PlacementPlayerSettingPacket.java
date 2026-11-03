package team.creative.littletiles.common.packet.action;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.network.CreativePacket;
import team.creative.littletiles.common.placement.setting.PlacementPlayerSetting;

public class PlacementPlayerSettingPacket extends CreativePacket {
    
    public PlacementPlayerSetting setting;
    
    public PlacementPlayerSettingPacket(PlacementPlayerSetting setting) {
        this.setting = setting;
    }
    
    public PlacementPlayerSettingPacket() {}
    
    public void executeClient(Player player) {
        // The client keeps its own placement settings; the server-side copy is only used for validation.
    }
    
    public void executeServer(ServerPlayer player) {
        PlacementPlayerSetting.setServer(player, setting);
    }
    
}
