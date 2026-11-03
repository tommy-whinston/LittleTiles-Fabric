package net.minecraftforge.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.sounds.SoundEvent;

public final class ForgeRegistries {

    private ForgeRegistries() {
    }

    public static final IForgeRegistry<Block> BLOCKS = new IForgeRegistry<>(BuiltInRegistries.BLOCK);
    public static final IForgeRegistry<Item> ITEMS = new IForgeRegistry<>(BuiltInRegistries.ITEM);
    public static final IForgeRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = new IForgeRegistry<>(BuiltInRegistries.BLOCK_ENTITY_TYPE);
    public static final IForgeRegistry<EntityType<?>> ENTITY_TYPES = new IForgeRegistry<>(BuiltInRegistries.ENTITY_TYPE);
    public static final IForgeRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = new IForgeRegistry<>(BuiltInRegistries.RECIPE_SERIALIZER);
    public static final IForgeRegistry<SoundEvent> SOUND_EVENTS = new IForgeRegistry<>(BuiltInRegistries.SOUND_EVENT);
}
