package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.skill.Skill;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Dawncraft.MODID, value = Side.CLIENT)
public class SkillRenderLoader
{
    @SubscribeEvent
    public static void registerSkillModels(ModelRegistryEvent event)
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
