package io.github.dawncraft.client.sound;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.GameData;

/**
 * Register some sounds.
 * 
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
@ObjectHolder(Dawncraft.MODID)
public class SoundInitializer
{
	public static final SoundEvent RECORDS_DJ = null;
	public static final SoundEvent RECORDS_WZSONGS = null;
	
    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
    	registerSound("records.dj");
    	registerSound("records.wzsongs");
    }
    
    /**
     * Register a sound with a string id.
     *
     * @param name The sound's string id
     */
    private static void registerSound(String name)
    {
        ForgeRegistries.SOUND_EVENTS.register(new SoundEvent(GameData.checkPrefix(name, true)));
    }
    
    public static ISound createSound(SoundEvent sound)
    {
        return PositionedSoundRecord.getRecord(sound, 1.0F, 1.0F);
    }
}
