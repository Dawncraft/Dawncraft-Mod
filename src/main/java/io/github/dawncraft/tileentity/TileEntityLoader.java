package io.github.dawncraft.tileentity;

import io.github.dawncraft.Dawncraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityLoader
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
        GameRegistry.registerTileEntity(tileEntityClass, Dawncraft.MODID + ":" + id);
    }
    
    private static void registerTileEntityWithAlias(Class<? extends TileEntity> tileEntityClass, String id, String... alternatives)
    {
        GameRegistry.registerTileEntityWithAlternatives(tileEntityClass, id, alternatives);
    }
}
