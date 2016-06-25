package WdawningStudio.DawnW.science.worldgen;

import java.util.Random;

import WdawningStudio.DawnW.science.block.BlockLoader;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGeneratorLoader
{
    public static IWorldGenerator magnetoreGenerator;

    public WorldGeneratorLoader()
    {
        magnetoreGenerator = new IWorldGenerator()
        {
        	public final WorldGenMinable magnetoreGenerator = new WorldGenMinable(BlockLoader.magnetOre.getDefaultState(), 8);

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
                        if (biomeGenBase.getFloatRainfall() < random.nextFloat())
                        {
                        	magnetoreGenerator.generate(world, random, blockpos);
                        }
                    }
                }
            }
        };
        GameRegistry.registerWorldGenerator(magnetoreGenerator, 6);
    }
}