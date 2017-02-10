package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.client.renderer.model.ModelMouse;
import io.github.dawncraft.entity.EntityMouse;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMouse extends RenderLiving<EntityMouse>
{
	private static final ResourceLocation MOUSE_TEXTURE = new ResourceLocation(dawncraft.MODID + ":" + "textures/entity/mouse.png");

	public RenderMouse(RenderManager renderManager)
	{
		super(renderManager, new ModelMouse(), 0.5F);
	}

	@Override
	protected void preRenderCallback(EntityMouse entity, float partialTickTime)
	{

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMouse entity)
	{
		return RenderMouse.MOUSE_TEXTURE;
	}

	@Override
	public void doRender(EntityMouse entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}