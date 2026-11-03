package team.creative.littletiles.common.structure.animation.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import team.creative.littletiles.common.structure.animation.context.AnimationContext;

public class PlaySoundEvent extends AnimationEvent<CompoundTag> {
    
    public static SoundEvent get(ResourceLocation location) {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(location);
        if (sound != null)
            return sound;
        return SoundEvent.createVariableRangeEvent(location);
    }
    
    public SoundEvent sound;
    public float volume;
    public float pitch;
    
    public PlaySoundEvent(CompoundTag nbt) {
        sound = get(new ResourceLocation(nbt.getString("s")));
        volume = nbt.getFloat("v");
        pitch = nbt.getFloat("p");
    }
    
    public PlaySoundEvent(SoundEvent event, float volume, float pitch) {
        this.sound = event;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    public CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("s", sound.getLocation().toString());
        nbt.putFloat("v", volume);
        nbt.putFloat("p", pitch);
        return nbt;
    }
    
    public void start(AnimationContext context) {
        context.play(sound, volume, pitch);
    }
    
    public boolean isDone(int ticksActive, AnimationContext context) {
        return true;
    }
    
    public PlaySoundEvent copy() {
        return new PlaySoundEvent(sound, volume, pitch);
    }
    
    public int reverseTick(int start, int duration, AnimationContext context) {
        return start;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof PlaySoundEvent other)
            return sound.equals(other.sound) && volume == other.volume && pitch == other.pitch;
        return false;
    }
    
}
