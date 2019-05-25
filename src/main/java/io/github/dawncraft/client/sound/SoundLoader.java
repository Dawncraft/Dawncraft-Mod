package io.github.dawncraft.client.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class SoundLoader
{
    public static void initSounds() {}
    
    public static ISound createSound(ResourceLocation soundResource)
    {
        return PositionedSoundRecord.create(soundResource, 1.0F);
    }
}
