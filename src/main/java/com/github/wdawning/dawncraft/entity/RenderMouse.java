package com.github.wdawning.dawncraft.entity;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMouse extends RenderLiving
{
	private static final ResourceLocation MOUSE_TEXTURE = new ResourceLocation(dawncraft.MODID + ":" + "textures/entity/mouse.png");

	public RenderMouse(RenderManager renderManager)
	{
		super(renderManager, new ModelMouse(), 0.5F);
	}

	protected void preRenderCallbackMouse(EntityMouse entity, float partialTickTime)
	{

	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialTickTime)
	{
		this.preRenderCallbackMouse((EntityMouse) entity, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return RenderMouse.MOUSE_TEXTURE;
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}