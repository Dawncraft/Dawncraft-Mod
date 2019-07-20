package io.github.dawncraft.skill;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.registry.ModRegistry;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Register some skills.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class SkillInit
{
    public static Skill attack = null;
    public static Skill heal = null;
    public static Skill fireball = null;

    @SubscribeEvent
    public static void registerSkills(RegistryEvent.Register<Skill> event)
    {
        registerSkill(new SkillInstant(3).setUnlocalizedName("attack").setCreativeTab(CreativeTabsLoader.tabSkills), "attack");
        registerSkill(new SkillInstant(3).setUnlocalizedName("heal").setCreativeTab(CreativeTabsLoader.tabSkills), "heal");
        registerSkill(new SkillProjectile(3).setUnlocalizedName("fireball").setCreativeTab(CreativeTabsLoader.tabSkills), "fireball");
    }

    /**
     * Register a skill with a name-id.
     *
     * @param skill The skill to register
     * @param name The skill's name-id
     */
    private static void registerSkill(Skill skill, String name)
    {
        ModRegistry.SKILLS.register(skill.setRegistryName(name));
    }
}
