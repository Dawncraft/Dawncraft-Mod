package io.github.dawncraft.client.sound;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
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
public class SoundInit
{
    @ObjectHolder("gun.basic.empty")
    public static final SoundEvent GUN_EMPTY = null;
    @ObjectHolder("gun.basic.reload")
    public static final SoundEvent GUN_RELOAD = null;
    @ObjectHolder("gun.basic.shoot")
    public static final SoundEvent GUN_SHOOT = null;
    @ObjectHolder("records.dj")
    public static final SoundEvent RECORDS_DJ = null;
    @ObjectHolder("records.wzsongs")
    public static final SoundEvent RECORDS_WZSONGS = null;
    @ObjectHolder("gui.error")
    public static final SoundEvent GUI_ERROR = null;
    public static final SoundEvent GUI_PRESS = SoundEvents.UI_BUTTON_CLICK;

    @ObjectHolder("mob.gerking.say")
    public static final SoundEvent GERKING_SAY = null;
    @ObjectHolder("mob.gerking.hurt")
    public static final SoundEvent GERKING_HURT = null;
    @ObjectHolder("mob.gerking.death")
    public static final SoundEvent GERKING_DEATH = null;

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
        registerSound("gun.basic.empty");
        registerSound("gun.basic.reload");
        registerSound("gun.basic.shoot");
        registerSound("records.dj");
        registerSound("records.wzsongs");
        registerSound("gui.error");
        registerSound("mob.gerking.say");
        registerSound("mob.gerking.hurt");
        registerSound("mob.gerking.death");
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

    @Deprecated
    public static ISound createSound(SoundEvent sound)
    {
        return PositionedSoundRecord.getRecord(sound, 1.0F, 1.0F);
    }
}
