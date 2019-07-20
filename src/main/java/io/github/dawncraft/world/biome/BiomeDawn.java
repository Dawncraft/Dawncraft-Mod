package io.github.dawncraft.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeDawn extends Biome
{
    public BiomeDawn(BiomeProperties properties)
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.topBlock = Blocks.DIRT.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return super.createBiomeDecorator();
    }
}
