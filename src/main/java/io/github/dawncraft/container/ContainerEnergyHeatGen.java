package io.github.dawncraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Iterator;

import io.github.dawncraft.tileentity.TileEntityEnergyHeatGen;

/**
 * @author QingChenW
 *
 */
public class ContainerEnergyHeatGen extends Container
{
    private final TileEntityEnergyHeatGen heatGenerator;
    private IItemHandler fuelItems;
    private int burnTime = 0;
    private int currentItemBurnTime = 0;
    private int electricity = 0;
    
    public ContainerEnergyHeatGen(EntityPlayer player, TileEntity tileEntity)
    {
        super();
        this.heatGenerator = (TileEntityEnergyHeatGen) tileEntity;
        
        this.addSlotToContainer(new SlotItemHandler(this.fuelItems, 0, 56, 30));
//        this.addSlotToContainer(new SlotFurnaceFuel(heatGenerator, 0, 55, 46));
        
        for(int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        for(int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = this.inventorySlots.get(index);
        ItemStack oldItemstack = null;
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack newItemstack = slot.getStack();
            oldItemstack = newItemstack.copy();
            Slot fuel = this.inventorySlots.get(0);
            
            // TileEntityHeatGenerator.isItemFuel(itemstack1) &&
            // (!fuel.getHasStack() || itemstack1 == fuel.getStack())
            if (index > 0)
            {
                if (!this.mergeItemStack(newItemstack, 0, 0, false)) { return null; }
            }
            else if (index >= 1 && index <= 27)
            {
                if (!this.mergeItemStack(newItemstack, 28, 37, false)) { return null; }
            }
            else if (index >= 28 && index <= 37)
            {
                if (!this.mergeItemStack(newItemstack, 1, 27, false)) { return null; }
            }
            else if (!this.mergeItemStack(newItemstack, 1, 36, false)) { return null; }
            
            if (newItemstack.stackSize <= 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }
            
            if (newItemstack.stackSize == oldItemstack.stackSize) { return null; }
            
            slot.onPickupFromSlot(playerIn, newItemstack);
        }
        return oldItemstack;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
         return player.getDistanceSq(this.heatGenerator.getPos()) <= 64.0D;
    }
    /* 
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
    
*/
}
