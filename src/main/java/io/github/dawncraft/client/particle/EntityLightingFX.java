package io.github.dawncraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityLightingFX extends EntityFX
{
    protected EntityLightingFX(World world, double posX, double posY, double posZ)
    {
        super(world, posX, posY, posZ);
    }
    
    @Override
    public void renderParticle(WorldRenderer worldRenderer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(entity.posX - 0.5D, entity.posY, entity.posZ - 0.5D).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
        worldrenderer.pos(entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }

    @Override
    public void onUpdate()
    {
        
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(int particleID, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... ints)
        {
            return new EntityLightingFX(world, xCoord, yCoord, zCoord);
        }
    }
}
