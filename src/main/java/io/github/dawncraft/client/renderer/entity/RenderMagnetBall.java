package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.EntityMagnetBall;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderMagnetBall extends RenderSnowball<EntityMagnetBall>
{
	public RenderMagnetBall(RenderManager renderManagerIn)
	{
		super(renderManagerIn, ItemLoader.magnetBall, Minecraft.getMinecraft().getRenderItem());
	}

}
