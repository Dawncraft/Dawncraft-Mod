package com.github.wdawning.dawncraft.worldgen;

import java.util.Random;

import com.github.wdawning.dawncraft.block.BlockLoader;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenMagetore extends WorldGenMinable implements IWorldGenerator
{
    public WorldGenMagetore()
    {
		super(BlockLoader.magnetOre.getDefaultState(), 8);
	}

	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.getDimensionId() == 0)
        {
            for (int i = 0; i < 8; ++i)
            {
                BlockPos blockpos = new BlockPos(chunkX * 16 + random.nextInt(16), 1 + random.nextInt(62),
                        chunkZ * 16 + random.nextInt(16));
                BiomeGenBase biomeGenBase = world.getBiomeGenForCoords(blockpos);
                super.generate(world, random, blockpos);
            }
        }
    }
}
