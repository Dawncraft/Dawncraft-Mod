package com.github.wdawning.dawncraft.entity;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSavage extends RenderBiped
{
	private static final ResourceLocation SAVAGE_TEXTURE = new ResourceLocation(dawncraft.MODID + ":" + "textures/entity/savage.png");

	public RenderSavage(RenderManager renderManager)
	{
		super(renderManager, new ModelBiped(), 0.5F);
	}

	protected void preRenderCallbackSavage(EntitySavage entity, float partialTickTime)
	{

	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialTickTime)
	{
		this.preRenderCallbackSavage((EntitySavage) entity, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return RenderSavage.SAVAGE_TEXTURE;
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}
