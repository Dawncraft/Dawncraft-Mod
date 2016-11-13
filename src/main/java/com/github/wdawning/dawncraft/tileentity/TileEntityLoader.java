package com.github.wdawning.dawncraft.tileentity;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.tileentity.generator.TileEntityHeatGenerator;

public class TileEntityLoader
{
    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityHeatGenerator.class, "EleHeatGenerator");
        registerTileEntity(TileEntityMachineEleFurnace.class, "MachineEleFurnace");
        // registerTileEntity(TileEntityWchest.class, "SuperChest");
    }
    
    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, dawncraft.MODID + ":" + id);
    }
}
