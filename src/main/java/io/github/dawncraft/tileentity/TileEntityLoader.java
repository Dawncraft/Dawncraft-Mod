package io.github.dawncraft.tileentity;

import io.github.dawncraft.dawncraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class TileEntityLoader
{
    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        this.registerTileEntity(TileEntityEnergyHeatGen.class, "HeatGenerator");
        //        registerTileEntity(TileEntityMachineFurnace.class, "MachineFurnace");
        // registerTileEntity(TileEntityWchest.class, "SuperChest");
        this.registerTileEntity(TileEntitySkull.class, "Skull");
        this.registerTileEntity(TileEntityMagnetDoor.class, "MagnetDoor");
    }
    
    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, dawncraft.MODID + ":" + id);
    }
}
