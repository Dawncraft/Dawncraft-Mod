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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.GameData;

/**
 * Register some sounds.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID, value = Side.CLIENT)
public class SoundInit
{
    public static SoundEvent GERKING_SAY;
    public static SoundEvent GERKING_HURT;
    public static SoundEvent GERKING_DEATH;

    public static SoundEvent GUN_EMPTY;
    public static SoundEvent GUN_RELOAD;
    public static SoundEvent GUN_SHOOT;

    public static SoundEvent RECORDS_DJ;
    public static SoundEvent RECORDS_WZSONGS;

    public static SoundEvent GUI_ERROR;
    public static SoundEvent GUI_PRESS = SoundEvents.UI_BUTTON_CLICK;

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
        GERKING_SAY = registerSound("mob.gerking.say", "gerking_say");
        GERKING_HURT = registerSound("mob.gerking.hurt", "gerking_hurt");
        GERKING_DEATH = registerSound("mob.gerking.death", "gerking_death");

        GUN_EMPTY = registerSound("gun.basic.empty", "gun_empty");
        GUN_RELOAD = registerSound("gun.basic.reload", "gun_reload");
        GUN_SHOOT = registerSound("gun.basic.shoot", "gun_shoot");

        RECORDS_DJ = registerSound("records.dj", "records_dj");
        RECORDS_WZSONGS = registerSound("records.wzsongs", "records_wzsongs");

        GUI_ERROR = registerSound("gui.error", "gui_error");
    }

    /**
     * Register a sound with a string id.
     *
     * @param name The sound's string id
     * @return
     */
    private static SoundEvent registerSound(String name, String id)
    {
        SoundEvent sound = new SoundEvent(GameData.checkPrefix(name, true));
        ForgeRegistries.SOUND_EVENTS.register(sound.setRegistryName(id));
        return sound;
    }

    @Deprecated
    public static ISound createSound(SoundEvent sound)
    {
        return PositionedSoundRecord.getRecord(sound, 1.0F, 1.0F);
    }
}
