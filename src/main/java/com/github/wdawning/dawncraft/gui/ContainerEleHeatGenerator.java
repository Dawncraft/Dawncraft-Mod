package com.github.wdawning.dawncraft.gui;

import java.util.Iterator;

import com.github.wdawning.dawncraft.tileentity.TileEntityEleHeatGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEleHeatGenerator extends Container
{
    private final TileEntityEleHeatGenerator eleHeatGenerator;
	private int lastBurnTime = 0;
	private int lastCurrentItemBurnTime = 0;
	private int lastElectric = 0;
    
	public ContainerEleHeatGenerator(InventoryPlayer playerInventory, TileEntityEleHeatGenerator tile) {
        this.eleHeatGenerator = tile;
        this.addSlotToContainer(new SlotFurnaceFuel(tile, 0, 55, 46));
        
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
		return this.eleHeatGenerator.isUseableByPlayer(player);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		this.eleHeatGenerator.setField(par1, par2);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		while (var1.hasNext())
		{
			ICrafting var2 = (ICrafting) var1.next();

			if (this.lastBurnTime != this.eleHeatGenerator.getField(0)) {
				var2.sendProgressBarUpdate(this, 0, this.eleHeatGenerator.getField(0));
			}

			if (this.lastCurrentItemBurnTime != this.eleHeatGenerator.getField(1)) {
				var2.sendProgressBarUpdate(this, 1, this.eleHeatGenerator.getField(1));
			}
			
			if (this.lastElectric != this.eleHeatGenerator.getField(2)) {
				var2.sendProgressBarUpdate(this, 2, this.eleHeatGenerator.getField(2));
			}
		}
		this.lastBurnTime = this.eleHeatGenerator.getField(0);
		this.lastCurrentItemBurnTime = this.eleHeatGenerator.getField(1);
		this.lastElectric = this.eleHeatGenerator.getField(2);
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
            
            if (TileEntityEleHeatGenerator.isItemFuel(itemstack1))
            {

            }
            else if (index >= 1 && index < 28)
            {
                if (!this.mergeItemStack(itemstack1, 28, 37, false))
                {
                    return null;
                }
            }
            else if (index >= 28 && index < 37)
            {
            	if (!this.mergeItemStack(itemstack1, 1, 27, false))
                {
                	return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 1, 37, false))
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
