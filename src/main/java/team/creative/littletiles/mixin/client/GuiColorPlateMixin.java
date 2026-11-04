package team.creative.littletiles.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiGraphics;
import team.creative.creativecore.common.gui.GuiChildControl;
import team.creative.creativecore.common.gui.controls.simple.GuiColorPlate;
import team.creative.creativecore.common.gui.style.display.DisplayColor;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.type.Color;

@Mixin(value = GuiColorPlate.class, remap = false)
public class GuiColorPlateMixin {
    
    @Shadow
    private Color color;
    
    @Shadow
    private DisplayColor colorPlate;
    
    @Inject(method = "renderContent", at = @At("HEAD"))
    private void ensureColorPlate(GuiGraphics graphics, GuiChildControl control, Rect rect, int mouseX, int mouseY, CallbackInfo info) {
        if (colorPlate == null) {
            if (color == null)
                color = new Color(255, 255, 255, 255);
            colorPlate = new DisplayColor(color);
        }
    }
    
}
