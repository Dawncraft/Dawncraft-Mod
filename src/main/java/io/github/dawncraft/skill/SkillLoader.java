package io.github.dawncraft.skill;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.skill.api.SkillRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author QingChenW
 *
 */
public class SkillLoader
{
    public static Skill attack = new Skill().setConsumeMana(4).setUnlocalizedName("skillAttack").setCreativeTab(CreativeTabsLoader.tabSkills);
    public static Skill heal = new Skill().setConsumeMana(4).setUnlocalizedName("skillHeal").setCreativeTab(CreativeTabsLoader.tabSkills);
    
    public SkillLoader(FMLPreInitializationEvent event)
    {
        register(attack, "attack");
        register(heal, "heal");
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
        SkillRegistry.registerSkill(skill, name);
    }
}
