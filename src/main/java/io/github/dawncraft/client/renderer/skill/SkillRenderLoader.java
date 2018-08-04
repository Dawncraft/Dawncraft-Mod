package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class SkillRenderLoader
{
    public SkillRenderLoader(FMLPreInitializationEvent event)
    {
        registerRender(SkillLoader.attack);
        registerRender(SkillLoader.heal);
        registerRender(SkillLoader.longPrepare);
        registerRender(SkillLoader.longSpell);
        registerRender(SkillLoader.longCooldown);
    }

    /**
     * Register a skill's inventory model.
     *
     * @param skill skill's string id
     */
    private static void registerRender(Skill skill)
    {
        for (int i = 0; i <= skill.getMaxLevel(); i++)
            registerRender(skill, i, skill.getRegistryName());
    }

    /**
     * Register a skill's inventory model with level and name.
     *
     * @param skill skill to register
     * @param level skill's level
     * @param name skill's model suffix
     */
    private static void registerRender(Skill skill, int level, String name)
    {
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(skill, level, model);
    }
}
