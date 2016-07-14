package com.github.wdawning.dawncraft.gui;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.tileentity.TileEntityEleHeatGenerator;
import com.github.wdawning.dawncraft.tileentity.TileEntityMachineEleFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiLoader implements IGuiHandler
{
	public GuiLoader(FMLPreInitializationEvent event)
	{
        NetworkRegistry.INSTANCE.registerGuiHandler(dawncraft.instance, this);
	}
	
	@Override 
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{ 
		switch(ID)
		{
			//
			case 33:
				return new ContainerEleHeatGenerator(player.inventory, (TileEntityEleHeatGenerator)player.worldObj.getTileEntity(new BlockPos(x, y, z)));
			//
			case 65:
				return new ContainerMachineEleFurnace(player.inventory, (TileEntityMachineEleFurnace)player.worldObj.getTileEntity(new BlockPos(x, y, z)));
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
		switch(ID)
		{
			//
			case 33:
				return new GuiEleHeatGenerator(player.inventory, (TileEntityEleHeatGenerator)player.worldObj.getTileEntity(new BlockPos(x, y, z)));
			//
			case 65:
				return new GuiMachineEleFurnace(player.inventory, (TileEntityMachineEleFurnace)player.worldObj.getTileEntity(new BlockPos(x, y, z)));
			//
					
			//
					
			//
		}
		return null;
	}
}