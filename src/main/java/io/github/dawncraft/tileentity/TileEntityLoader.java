package io.github.dawncraft.tileentity;

import io.github.dawncraft.Dawncraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityLoader
{
    public static void initTileEntities()
    {
        registerTileEntity(TileEntityEnergyGenerator.class, "EnergyGenerator");
        registerTileEntity(TileEntityMachineFurnace.class, "MachineFurnace");
        // registerTileEntity(TileEntityWchest.class, "SuperChest");
        registerTileEntity(TileEntitySkull.class, "Skull");
        registerTileEntity(TileEntityMagnetDoor.class, "MagnetDoor");
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, Dawncraft.MODID + ":" + id);
    }
}
