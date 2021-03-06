package io.github.dawncraft.world;

import io.github.dawncraft.world.biome.BiomeInit;
import io.github.dawncraft.world.gen.ChunkGeneratorDawn;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderDawn extends WorldProvider
{
    @Override
    public DimensionType getDimensionType()
    {
        return WorldInit.DAWNWORLD;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public int getAverageGroundLevel()
    {
        return 50;
    }

    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.FAIRY_LAND);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorDawn(this.world, this.world.getSeed(), this.world.getWorldInfo().isMapFeaturesEnabled());
    }
}
