package io.github.dawncraft.world;

import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

//TODO 天域
public class WorldLoader
{
    public static final int DAWNWORLD = 23;

    public static void initWorlds()
    {
        registerWorld(DAWNWORLD, WorldProviderDawn.class, true);
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
