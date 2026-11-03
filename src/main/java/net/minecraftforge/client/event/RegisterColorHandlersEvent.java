package net.minecraftforge.client.event;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class RegisterColorHandlersEvent extends net.minecraftforge.eventbus.api.Event {

    public static class Block extends RegisterColorHandlersEvent {

        public void register(BlockColor color, net.minecraft.world.level.block.Block... blocks) {
        }
    }

    public static class Item extends RegisterColorHandlersEvent {

        public void register(ItemColor color, ItemLike... items) {
        }

        public ItemColors getItemColors() {
            return new ItemColors();
        }

        public BlockColors getBlockColors() {
            return new BlockColors();
        }
    }
}
