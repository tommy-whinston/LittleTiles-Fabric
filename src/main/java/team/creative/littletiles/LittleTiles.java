/*
 * LittleTiles-Fabric - unofficial Fabric 1.20.1 port of LittleTiles.
 * Original LittleTiles: Copyright (C) CreativeMD and contributors.
 * This port is AI-assisted and licensed under LGPL-3.0-only.
 */
package team.creative.littletiles;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.creative.creativecore.CreativeCore;

public class LittleTiles implements ModInitializer {

    public static final String MODID = "littletiles";
    public static final String MOD_NAME = "LittleTiles-Fabric";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("{} starting (CreativeCore: {})", MOD_NAME, CreativeCore.class.getSimpleName());

        // TODO: port registries, block entities, network packets, events and capabilities.
    }
}
