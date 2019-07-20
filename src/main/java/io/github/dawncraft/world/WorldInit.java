package io.github.dawncraft.world;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class WorldInit
{
    private static final int DAWNWORLD_ID = 23;
    public static final DimensionType DAWNWORLD = DimensionType.register("dawnworld", "_dawn", DAWNWORLD_ID, WorldProviderDawn.class, true);

    public static void initWorlds()
    {
        registerWorld(DAWNWORLD_ID, DAWNWORLD);
    }

    /**
     * Register a world.
     *
     * @param id The world's id
     * @param dimensionType The world's dimension type
     */
    private static void registerWorld(int id, DimensionType dimensionType)
    {
        DimensionManager.registerDimension(id, dimensionType);
    }
}
