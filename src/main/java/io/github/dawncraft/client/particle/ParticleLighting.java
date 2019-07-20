package io.github.dawncraft.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleLighting extends Particle
{
    protected ParticleLighting(World world, double posX, double posY, double posZ)
    {
        super(world, posX, posY, posZ);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        buffer.begin(5, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(entity.posX - 0.5D, entity.posY, entity.posZ - 0.5D).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
        buffer.pos(entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
    }

    @Override
    public void onUpdate()
    {

    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        @Override
        public Particle createParticle(int particleID, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... ints)
        {
            return new ParticleLighting(world, xCoord, yCoord, zCoord);
        }
    }
}
