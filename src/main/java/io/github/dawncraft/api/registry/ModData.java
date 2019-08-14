package io.github.dawncraft.api.registry;

import java.lang.reflect.Method;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.talent.Talent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * Register mod's registry.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class ModData
{
    public static final ResourceLocation SKILLS = new ResourceLocation(Dawncraft.MODID, "skills");
    public static final ResourceLocation TALENTS = new ResourceLocation(Dawncraft.MODID, "talents");

    private static final int MAX_SKILL_ID = 31999;
    private static final int MAX_TALENT_ID = 4095;

    @SubscribeEvent
    public static void newRegistry(RegistryEvent.NewRegistry event)
    {
        makeVanillaRegistry(SKILLS, Skill.class, MAX_SKILL_ID).create();
        makeRegistry(TALENTS, Talent.class, MAX_TALENT_ID).create();
    }

    /**
     * Make a registry builder.
     *
     * @param <T>
     * @param name
     * @param type
     * @param max
     * @return
     */
    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type, int max)
    {
        return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(max);
    }

    /**
     * Make a registry builder with forge's vanilla wrapper.
     * <br>The vanilla wrapper is private, so I use reflection.</br>
     *
     * @param <T>
     * @param name
     * @param type
     * @param max
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeVanillaRegistry(ResourceLocation name, Class<T> type, int max)
    {
        try
        {
            Method method = ObfuscationReflectionHelper.findMethod(GameData.class, "makeRegistry", RegistryBuilder.class, ResourceLocation.class, Class.class, int.class);
            return (RegistryBuilder<T>) method.invoke(null, name, type, max);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
