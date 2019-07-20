package io.github.dawncraft.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.projectile.EntityBullet;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends Render<EntityBullet>
{
    private static final ResourceLocation bulletTextures = new ResourceLocation(Dawncraft.MODID + ":" + "textures/entity/bullet.png");

    public RenderBullet(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    public void doRender(EntityBullet entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.bindEntityTexture(entity);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        int i = 0;
        float f = 0.0F;
        float f1 = 0.5F;
        float f2 = (0 + i * 10) / 32.0F;
        float f3 = (5 + i * 10) / 32.0F;
        float f4 = 0.0F;
        float f5 = 0.15625F;
        float f6 = (5 + i * 10) / 32.0F;
        float f7 = (10 + i * 10) / 32.0F;
        float f8 = 0.05625F;
        GlStateManager.enableRescaleNormal();

        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f8, f8, f8);
        GlStateManager.translate(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f8, 0.0F, 0.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex(f4, f6).endVertex();
        worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex(f5, f6).endVertex();
        worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex(f5, f7).endVertex();
        worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex(f4, f7).endVertex();
        tessellator.draw();
        GL11.glNormal3f(-f8, 0.0F, 0.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex(f4, f6).endVertex();
        worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex(f5, f6).endVertex();
        worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex(f5, f7).endVertex();
        worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex(f4, f7).endVertex();
        tessellator.draw();

        for (int j = 0; j < 4; ++j)
        {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f8);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-8.0D, -2.0D, 0.0D).tex(f, f2).endVertex();
            worldrenderer.pos(8.0D, -2.0D, 0.0D).tex(f1, f2).endVertex();
            worldrenderer.pos(8.0D, 2.0D, 0.0D).tex(f1, f3).endVertex();
            worldrenderer.pos(-8.0D, 2.0D, 0.0D).tex(f, f3).endVertex();
            tessellator.draw();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBullet entity)
    {
        return RenderBullet.bulletTextures;
    }
}
