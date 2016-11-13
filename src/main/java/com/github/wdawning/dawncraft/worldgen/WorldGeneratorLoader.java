package com.github.wdawning.dawncraft.worldgen;

import net.minecraft.world.WorldProvider;

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