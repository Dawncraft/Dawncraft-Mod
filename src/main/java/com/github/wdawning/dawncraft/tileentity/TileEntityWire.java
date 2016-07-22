package com.github.wdawning.dawncraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWire extends TileEntity implements IUpdatePlayerListBox
{
	@Override
	public void update() {
		// TODO 自动生成的方法存根
		
	}
	
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
    }
}
