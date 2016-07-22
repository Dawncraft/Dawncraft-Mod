package com.github.wdawning.dawncraft.tileentity;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityLoader
{
    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityEleHeatGenerator.class, "EleHeatGenerator");
        registerTileEntity(TileEntityMachineEleFurnace.class, "MachineEleFurnace");
//        registerTileEntity(TileEntityWchest.class, "SuperChest");
    }

    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, dawncraft.MODID + ":" + id);
    }
}