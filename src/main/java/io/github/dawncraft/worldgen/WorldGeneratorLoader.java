package io.github.dawncraft.worldgen;

import io.github.dawncraft.block.BlockLoader;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register new worlds.
 * 
 * @author QingChenW
 */
public class WorldGeneratorLoader
{

    
    public WorldGeneratorLoader(FMLInitializationEvent event)
    {
        new WorldOreGenerator(event);
        
        registerWorld(23, WorldProviderDawn.class);
    }
    
    /**
     * Register a world.
     * 
     * @param id The world's id
     * @param provider The world's provider
     */
    public void registerWorld(int id, Class<? extends WorldProvider> provider)
    {
        DimensionManager.registerProviderType(id, provider, true);
        DimensionManager.registerDimension(id, id);
    }
}
