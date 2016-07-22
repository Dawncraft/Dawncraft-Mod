package com.github.wdawning.dawncraft.gui;

import java.util.Iterator;

import com.github.wdawning.dawncraft.tileentity.TileEntityMachineEleFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMachineEleFurnace extends Container
{
    private final TileEntityMachineEleFurnace machineEleFurnace;
	private int lastCookTime = 0;
	private int lastTotalCookTime = 0;
    
	public ContainerMachineEleFurnace(InventoryPlayer playerInventory, TileEntityMachineEleFurnace tile) {
        this.machineEleFurnace = tile;
        this.addSlotToContainer(new Slot(tile, 0, 56, 26));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, tile, 1, 116, 35));
        
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.machineEleFurnace.isUseableByPlayer(player);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		this.machineEleFurnace.setField(par1, par2);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		while (var1.hasNext())
		{
			ICrafting var2 = (ICrafting) var1.next();

			if (this.lastCookTime != this.machineEleFurnace.getField(0)) {
				var2.sendProgressBarUpdate(this, 0, this.machineEleFurnace.getField(0));
			}

			if (this.lastTotalCookTime != this.machineEleFurnace.getField(1)) {
				var2.sendProgressBarUpdate(this, 1, this.machineEleFurnace.getField(1));
			}
		}
		this.lastCookTime = this.machineEleFurnace.getField(0);
		this.lastTotalCookTime = this.machineEleFurnace.getField(1);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index != 1 && index != 0)
            {
                if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (index >= 2 && index < 29)
                {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false))
                    {
                        return null;
                    }
                }
                else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 2, 38, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }
}
