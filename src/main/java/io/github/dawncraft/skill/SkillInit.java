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
    public static Skill ATTACK;
    public static Skill HEAL;
    public static Skill FIREBALL;

    @SubscribeEvent
    public static void registerSkills(RegistryEvent.Register<Skill> event)
    {
        ATTACK = registerSkill(new SkillInstant(3).setTranslationKey("attack").setCreativeTab(CreativeTabsLoader.SKILLS), "attack");
        HEAL = registerSkill(new SkillInstant(3).setTranslationKey("heal").setCreativeTab(CreativeTabsLoader.SKILLS), "heal");
        FIREBALL = registerSkill(new SkillProjectile(3).setTranslationKey("fireball").setCreativeTab(CreativeTabsLoader.SKILLS), "fireball");
    }

    /**
     * Register a skill with a name-id.
     *
     * @param skill The skill to register
     * @param name The skill's name-id
     */
    private static Skill registerSkill(Skill skill, String name)
    {
        ModRegistry.SKILLS.register(skill.setRegistryName(name));
        return skill;
    }
}
