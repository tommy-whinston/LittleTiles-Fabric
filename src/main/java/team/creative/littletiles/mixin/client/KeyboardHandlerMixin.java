package team.creative.littletiles.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.KeyboardHandler;
import team.creative.littletiles.client.LittleTilesClient;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    
    @Inject(method = "keyPress", at = @At("HEAD"))
    private void keyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        if (action == 1)
            LittleTilesClient.onKeyPressed(key, scancode);
    }
    
}
