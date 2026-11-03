package net.minecraftforge.event.entity.player;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class EntityItemPickupEvent extends PlayerEvent {

    private final ItemEntity item;

    public EntityItemPickupEvent() {
        this.item = null;
    }

    public EntityItemPickupEvent(Player player, ItemEntity item) {
        super(player);
        this.item = item;
    }

    public ItemEntity getItem() {
        return item;
    }
}
