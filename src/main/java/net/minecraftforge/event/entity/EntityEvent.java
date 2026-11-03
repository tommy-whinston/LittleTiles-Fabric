package net.minecraftforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;

public class EntityEvent extends net.minecraftforge.eventbus.api.Event {

    private final Entity entity;

    public EntityEvent() {
        this.entity = null;
    }

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public static class Size extends EntityEvent {

        private EntityDimensions newSize;

        public Size() {
            this.newSize = null;
        }

        public Size(Entity entity, EntityDimensions size) {
            super(entity);
            this.newSize = size;
        }

        public EntityDimensions getNewSize() {
            return newSize;
        }

        public void setNewSize(EntityDimensions newSize) {
            this.newSize = newSize;
        }
    }
}
