package io.github.dawncraft.api;

import com.google.common.base.Strings;
import io.github.dawncraft.skill.Skill;

/**
 * Register something added by Dawncraft mod.
 *
 * @author QingChenW
 */
public class ModRegistry
{
    /**
     * Register a skill with the skill registry with a the name specified in Skill.getRegistryName()
     *
     * @param skill The skill to register
     */
    public static void registerSkill(Skill skill)
    {
        registerSkill(skill, skill.getRegistryName());
    }
    
    /**
     * Register a skill with the skill registry with a custom name
     *
     * @param skill The skill to register
     * @param name The mod-unique name of the skill
     */
    public static void registerSkill(Skill skill, String name)
    {
        if (Strings.isNullOrEmpty(name))
        {
            throw new IllegalArgumentException("Attempted to register a skill with no name: " + skill);
        }
        ModData.getMain().registerSkill(skill, name);
    }
    
    /**
     * Look up a mod skill in the global "named skill list"
     *
     * @param modId The modid owning the skill
     * @param name  The name of the skill itself
     * @return The skill or null if not found
     */
    public static Skill findSkill(String modId, String name)
    {
        return ModData.findSkill(modId, name);
    }
}
