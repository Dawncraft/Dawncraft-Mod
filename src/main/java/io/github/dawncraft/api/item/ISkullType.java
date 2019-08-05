package io.github.dawncraft.api.item;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ISkullType
{
    Class<? extends Entity> getEntityClass();

    String getEntityName();

    @SideOnly(Side.CLIENT)
    ResourceLocation getEntityTexure(RenderManager renderManager);

    @SideOnly(Side.CLIENT)
    ModelSkeletonHead getEntitySkull();
}
