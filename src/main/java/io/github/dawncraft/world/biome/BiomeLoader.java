package io.github.dawncraft.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class BiomeLoader
{
    public static BiomeGenBase fairyland = null;

    public BiomeLoader(FMLInitializationEvent event)
    {

    }
    
    public static void registerBiome(BiomeType type, BiomeGenBase biome, int weight)
    {
        BiomeManager.addBiome(type, new BiomeEntry(biome, weight));
    }
    
    public static void registerOceanBiome(BiomeGenBase biome)
    {
        BiomeManager.oceanBiomes.add(biome);
    }

    public static void registerBiomeTypes(BiomeGenBase biome, BiomeDictionary.Type... types)
    {
        BiomeDictionary.registerBiomeType(biome, types);
    }
}
