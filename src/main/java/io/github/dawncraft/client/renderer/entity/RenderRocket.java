package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.projectile.EntityRocket;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class RenderRocket extends RenderSnowball<EntityRocket>
{
    public RenderRocket(RenderManager renderManager)
    {
        super(renderManager, ItemInit.gunRocket, Minecraft.getMinecraft().getRenderItem());
    }

}
