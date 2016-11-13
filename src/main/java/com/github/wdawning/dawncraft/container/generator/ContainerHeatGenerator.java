package com.github.wdawning.dawncraft.container.generator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;

import com.github.wdawning.dawncraft.tileentity.generator.TileEntityHeatGenerator;

public class ContainerHeatGenerator extends Container
{
    private final TileEntityHeatGenerator heatGenerator;
    private int lastBurnTime = 0;
    private int lastCurrentItemBurnTime = 0;
    private int lastElectric = 0;
    
    public ContainerHeatGenerator(InventoryPlayer playerInventory, TileEntityHeatGenerator tile)
    {
        this.heatGenerator = tile;
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
        return this.heatGenerator.isUseableByPlayer(player);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        this.heatGenerator.setField(par1, par2);
    }
    
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        Iterator var1 = this.crafters.iterator();
        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting) var1.next();
            
            if (this.lastBurnTime != this.heatGenerator.getField(0))
            {
                var2.sendProgressBarUpdate(this, 0, this.heatGenerator.getField(0));
            }
            
            if (this.lastCurrentItemBurnTime != this.heatGenerator.getField(1))
            {
                var2.sendProgressBarUpdate(this, 1, this.heatGenerator.getField(1));
            }
            
            if (this.lastElectric != this.heatGenerator.getField(2))
            {
                var2.sendProgressBarUpdate(this, 2, this.heatGenerator.getField(2));
            }
        }
        this.lastBurnTime = this.heatGenerator.getField(0);
        this.lastCurrentItemBurnTime = this.heatGenerator.getField(1);
        this.lastElectric = this.heatGenerator.getField(2);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            Slot fuel = (Slot) this.inventorySlots.get(0);
            
            // TileEntityHeatGenerator.isItemFuel(itemstack1) &&
            // (!fuel.getHasStack() || itemstack1 == fuel.getStack())
            if (index > 0)
            {
                if (!this.mergeItemStack(itemstack1, 0, 0, false)) { return null; }
            }
            else if (index >= 1 && index <= 27)
            {
                if (!this.mergeItemStack(itemstack1, 28, 37, false)) { return null; }
            }
            else if (index >= 28 && index <= 37)
            {
                if (!this.mergeItemStack(itemstack1, 1, 27, false)) { return null; }
            }
            else if (!this.mergeItemStack(itemstack1, 1, 36, false)) { return null; }
            
            if (itemstack1.stackSize <= 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }
            
            if (itemstack1.stackSize == itemstack.stackSize) { return null; }
            
            slot.onPickupFromSlot(playerIn, itemstack1);
        }
        
        return itemstack;
    }
}
