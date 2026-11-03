package team.creative.creativecore.mixin;

import net.minecraftforge.client.model.lighting.QuadLighter;

public interface ForgeModelBlockRendererAccessor {

    ThreadLocal<QuadLighter> getSmoothLighter();

    ThreadLocal<QuadLighter> getFlatLighter();
}
