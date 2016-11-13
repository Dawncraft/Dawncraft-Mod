package com.github.wdawning.dawncraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.client.gui.generator.GuiHeatGenerator;
import com.github.wdawning.dawncraft.container.ContainerMachineEleFurnace;
import com.github.wdawning.dawncraft.container.generator.ContainerHeatGenerator;
import com.github.wdawning.dawncraft.tileentity.TileEntityMachineEleFurnace;
import com.github.wdawning.dawncraft.tileentity.generator.TileEntityHeatGenerator;

public class GuiLoader implements IGuiHandler
{
    public static final int GUI_HEAT_GENERATOR = 33;
    
    public static final int GUI_ELECTRICITY_FURNACE = 65;
    
    public GuiLoader(FMLPreInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(dawncraft.instance, this);
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        //
        case GUI_HEAT_GENERATOR:
            return new ContainerHeatGenerator(player.inventory,
                    (TileEntityHeatGenerator) player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        //
        case GUI_ELECTRICITY_FURNACE:
            return new ContainerMachineEleFurnace(player.inventory,
                    (TileEntityMachineEleFurnace) player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        //
        
        //
        
        //
        
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        //
        case GUI_HEAT_GENERATOR:
            return new GuiHeatGenerator(player.inventory,
                    (TileEntityHeatGenerator) player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        //
        case GUI_ELECTRICITY_FURNACE:
            return new GuiMachineEleFurnace(player.inventory,
                    (TileEntityMachineEleFurnace) player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        //
        
        //
        
        //
        }
        return null;
    }
}
