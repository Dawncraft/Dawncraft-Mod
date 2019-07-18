package io.github.dawncraft.skill;

import io.github.dawncraft.api.registry.ModRegistry;
import io.github.dawncraft.creativetab.CreativeTabsLoader;

/**
 * Register some skills.
 *
 * @author QingChenW
 */
public class SkillLoader
{
    public static Skill attack = new SkillInstant(3).setUnlocalizedName("attack").setCreativeTab(CreativeTabsLoader.tabSkills);
    public static Skill heal = new SkillInstant(3).setUnlocalizedName("heal").setCreativeTab(CreativeTabsLoader.tabSkills);
    public static Skill fireball = new SkillProjectile(3).setUnlocalizedName("fireball").setCreativeTab(CreativeTabsLoader.tabSkills);

    public static void initSkills()
    {
        register(attack, "attack");
        register(heal, "heal");
        register(fireball, "fireball");
    }
    
    /**
     * Register a skill with a name-id.
     *
     * @param skill
     *            The skill to register
     * @param name
     *            The skill's name-id
     */
    private static void register(Skill skill, String name)
    {
        ModRegistry.registerSkill(skill, name);
    }
}
