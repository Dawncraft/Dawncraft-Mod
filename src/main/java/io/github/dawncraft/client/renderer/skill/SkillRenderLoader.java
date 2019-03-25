package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class SkillRenderLoader
{
    public static void initSkillRender()
    {
        registerSkill(SkillLoader.attack);
        registerSkill(SkillLoader.heal);
        registerSkill(SkillLoader.longPrepare);
        registerSkill(SkillLoader.longSpell);
        registerSkill(SkillLoader.longCooldown);
    }
    
    /**
     * Register a skill's inventory model.
     *
     * @param skill skill's string id
     */
    private static void registerSkill(Skill skill)
    {
        for (int i = 0; i <= skill.getMaxLevel(); i++)
            registerSkill(skill, i, skill.getRegistryName());
    }
    
    /**
     * Register a skill's inventory model with level and name.
     *
     * @param skill skill to register
     * @param level skill's level
     * @param name skill's model suffix
     */
    private static void registerSkill(Skill skill, int level, String name)
    {
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(skill, level, model);
    }
}
