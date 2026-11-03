package net.minecraftforge.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * Compile-time stand-in for Forge's multipart entity base. A Fabric implementation
 * will be added when multipart entities are ported.
 */
public abstract class PartEntity extends Entity {

    protected PartEntity(EntityType<?> type, Level level) {
        super(type, level);
    }
}
