package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface SkillMeshDefinition
{
    ResourceLocation getTextureLocation(SkillStack stack);
}
