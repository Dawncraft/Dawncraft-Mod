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
        registerTileEntity(TileEntityMagnetChest.class, "MagnetChest");
        registerTileEntity(TileEntityMagnetDoor.class, "MagnetDoor");
        registerTileEntity(TileEntityMachineFurnace.class, "MachineFurnace");
        registerTileEntity(TileEntitySkull.class, "Skull");
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, GameData.checkPrefix(id, true));
    }
}
