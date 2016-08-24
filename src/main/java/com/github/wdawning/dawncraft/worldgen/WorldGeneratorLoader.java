package com.github.wdawning.dawncraft.worldgen;

import java.util.Random;

import com.github.wdawning.dawncraft.block.BlockLoader;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGeneratorLoader
{
    public static IWorldGenerator magnetoreGenerator = new WorldGenMagetore();

    public WorldGeneratorLoader()
    {
        GameRegistry.registerWorldGenerator(magnetoreGenerator, 6);
        
        registerWorld(23, WorldProviderDawn.class);
    }
    
    public void registerWorld(int id, Class<? extends WorldProvider> provider)
    {
        DimensionManager.registerProviderType(id, provider, true);
        DimensionManager.registerDimension(id,id);
    }
}