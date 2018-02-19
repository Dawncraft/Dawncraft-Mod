package io.github.dawncraft.client.renderer.skill;

import java.util.Set;

import com.google.common.collect.Sets;

import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class SkillRenderLoader
{
    private static final Set<ResourceLocation> textures = Sets.newHashSet();

    public SkillRenderLoader(FMLPreInitializationEvent event)
    {
        registerRender(SkillLoader.attack);
        registerRender(SkillLoader.heal);
        registerRender(SkillLoader.longPrepare);
        registerRender(SkillLoader.longSpell);
        registerRender(SkillLoader.longCooldown);
    }

    private static void registerRender(Skill skill)
    {
        registerRender(skill, -1);
    }

    private static void registerRender(Skill skill, int level)
    {
        registerRender(skill, level, skill.getRegistryName());
    }

    private static void registerRender(Skill skill, int level, String name)
    {
        if(name != null)
        {
            textures.add(new ResourceLocation(name));
        }
        else
        {
            LogLoader.logger().error("Can't register skill which have not name: " + skill.toString());
        }
    }
    
    public static Set<ResourceLocation> getTextures()
    {
        return SkillRenderLoader.textures;
    }

    public static void cleanTextures()
    {
        SkillRenderLoader.textures.clear();
    }
}
