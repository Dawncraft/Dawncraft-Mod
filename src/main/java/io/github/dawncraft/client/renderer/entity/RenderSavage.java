package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.entity.EntitySavage;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSavage extends RenderBiped<EntitySavage>
{
    private static final ResourceLocation SAVAGE_TEXTURE = new ResourceLocation(dawncraft.MODID + ":" + "textures/entity/savage.png");

    public RenderSavage(RenderManager renderManager)
    {
        super(renderManager, new ModelBiped(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntitySavage entity, float partialTickTime)
    {

    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySavage entity)
    {
        return RenderSavage.SAVAGE_TEXTURE;
    }

    @Override
    public void doRender(EntitySavage entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
