package net.minecraftforge.common;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class ForgeSoundType extends SoundType {

    public ForgeSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound,
            Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound) {
        super(volume, pitch, breakSound.get(), stepSound.get(), placeSound.get(), hitSound.get(), fallSound.get());
    }
}
