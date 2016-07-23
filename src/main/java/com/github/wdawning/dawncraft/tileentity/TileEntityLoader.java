package com.github.wdawning.dawncraft.tileentity;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLoader
{
    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityEleHeatGenerator.class, "EleHeatGenerator");
        registerTileEntity(TileEntityMachineEleFurnace.class, "MachineEleFurnace");
//        registerTileEntity(TileEntityWchest.class, "SuperChest");
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
    	
    }
    
    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, dawncraft.MODID + ":" + id);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerTileEntityRender(Class<? extends TileEntity> tileEntityClass, TileEntitySpecialRenderer renderer)
    {
    	ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
}