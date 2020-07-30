package io.github.dawncraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;

/**
 * Register some block entities
 *
 * @author QingChenW
 */
public class TileEntityInit
{
    public static void initTileEntities()
    {
        registerTileEntity(TileEntityEnergyGenerator.class, "EnergyGenerator");
        registerTileEntity(TileEntityMachineFurnace.class, "MachineFurnace");
        registerTileEntity(TileEntitySkull.class, "Skull");
    }

    /**
     * Register a tile entity
     *
     * @param tileEntityClass
     * @param id
     */
    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, GameData.checkPrefix(id, true));
    }
}
