package team.creative.littletiles.client.compat;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import team.creative.creativecore.client.render.model.CreativeBlockModel;
import team.creative.creativecore.client.render.model.CreativeItemModel;

/**
 * Placeholder for CreativeCore's Forge-only client model registry. The model loading
 * will be ported to Fabric's model API in a later stage.
 */
public final class ClientRegistryCompat {

    private ClientRegistryCompat() {
    }

    public static void registerBlockModel(ResourceLocation location, CreativeBlockModel model) {
    }

    public static void registerItemModel(ResourceLocation location, CreativeItemModel model) {
    }

    public static void registerItemColor(ItemColors colors, Item... items) {
    }
}
