package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.skill.Skill;

public class SkillRenderLoader
{
    public static void initSkillRender()
    {
        // 技能物品栏模型现已自动注册
    }

    /**
     * Register a skill's inventory model with a custom SkillMeshDefinition.
     *
     * @param skill skill's string id
     */
    private static void registerSkill(Skill skill, SkillMeshDefinition meshDefinition)
    {
        ModelLoader.setCustomMeshDefinition(skill, meshDefinition);
    }
}
