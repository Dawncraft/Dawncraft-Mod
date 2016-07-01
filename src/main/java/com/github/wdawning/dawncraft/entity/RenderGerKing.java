package com.github.wdawning.dawncraft.entity;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGerKing extends RenderBiped
{
	private static final ResourceLocation GERKING_TEXTURE = new ResourceLocation(dawncraft.MODID + ":" + "textures/entity/ger_king.png");

	public RenderGerKing(RenderManager renderManager)
	{
		super(renderManager, new ModelBiped(), 1.0F);
	}

	protected void preRenderCallbackGerKing(EntityLivingBase entity, float partialTickTime)
	{
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialTickTime)
	{
		this.preRenderCallbackGerKing((EntityGerKing) entity, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return RenderGerKing.GERKING_TEXTURE;
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}
