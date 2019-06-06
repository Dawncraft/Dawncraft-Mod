package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.projectile.EntityMagnetBall;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class RenderMagnetBall extends RenderSnowball<EntityMagnetBall>
{
    public RenderMagnetBall(RenderManager renderManager)
    {
        super(renderManager, ItemLoader.magnetBall, Minecraft.getMinecraft().getRenderItem());
    }

}
