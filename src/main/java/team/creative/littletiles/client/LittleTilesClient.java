/*
 * LittleTiles-Fabric - unofficial Fabric 1.20.1 port of LittleTiles.
 * Original LittleTiles: Copyright (C) CreativeMD and contributors.
 * This port is AI-assisted and licensed under LGPL-3.0-only.
 */
package team.creative.littletiles.client;

import net.fabricmc.api.ClientModInitializer;
import team.creative.littletiles.LittleTiles;

public class LittleTilesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LittleTiles.LOGGER.info("{} client starting", LittleTiles.MOD_NAME);

        // TODO: port key mappings, block entity renderers, models and overlays.
    }
}
