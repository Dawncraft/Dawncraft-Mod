package io.github.dawncraft.world.biome;

import io.github.dawncraft.Dawncraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
@ObjectHolder(Dawncraft.MODID)
public class BiomeInit
{
    public static final Biome FAIRY_LAND = null;

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event)
    {
        registerBiome(new BiomeDawn(new Biome.BiomeProperties("Fairy Land")), "fairy_land");
    }

    private static void registerBiome(Biome biome, String name)
    {
        ForgeRegistries.BIOMES.register(biome.setRegistryName(name));
    }

    @Deprecated
    private static void registerBiome(BiomeType type, Biome biome, int weight)
    {
        BiomeManager.addBiome(type, new BiomeEntry(biome, weight));
    }

    @Deprecated
    private static void registerOceanBiome(Biome biome)
    {
        BiomeManager.oceanBiomes.add(biome);
    }

    @Deprecated
    private static void registerBiomeTypes(Biome biome, BiomeDictionary.Type... types)
    {
        BiomeDictionary.addTypes(biome, types);
    }
}