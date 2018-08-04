package io.github.dawncraft.client.renderer.model;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface SkillMeshDefinition
{
    ModelResourceLocation getModelLocation(SkillStack stack);
}
