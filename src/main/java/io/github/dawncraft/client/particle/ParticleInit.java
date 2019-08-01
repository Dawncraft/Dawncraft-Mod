package io.github.dawncraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.util.EnumParticleTypes;

import io.github.dawncraft.client.DawnEnumHelperClient;

public class ParticleInit
{
    public static final EnumParticleTypes LIGHTING = DawnEnumHelperClient.addEnumParticleType("LIGHTING", "lighting", 129, true);
    
    public static void initParticles()
    {
        registerParticle(LIGHTING, new ParticleLighting.Factory());
    }

    public static void registerParticle(EnumParticleTypes particleType, IParticleFactory particleFactory)
    {
        Minecraft.getMinecraft().effectRenderer.registerParticle(particleType.getParticleID(), particleFactory);
    }
}
