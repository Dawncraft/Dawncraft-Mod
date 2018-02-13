package io.github.dawncraft.world;

import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

//TODO 天域
public class WorldLoader
{
    public WorldLoader(FMLInitializationEvent event)
    {
        registerWorld(23, WorldProviderDawn.class, true);
    }

    /**
     * Register a world.
     *
     * @param id The world's id
     * @param provider The world's provider
     */
    public static void registerWorld(int id, Class<? extends WorldProvider> provider, boolean keepLoad)
    {
        DimensionManager.registerProviderType(id, provider, keepLoad);
        DimensionManager.registerDimension(id, id);
    }
}
