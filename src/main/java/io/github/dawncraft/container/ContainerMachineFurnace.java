package io.github.dawncraft.container;

import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 *
 * @author QingChenW
 */
public class ContainerMachineFurnace extends Container
{
    public final TileEntityMachineFurnace tileFurnace;
    public IItemHandler upItems;
    public IItemHandler downItems;
    public int electricity;
    public int cookTime;
    public int totalCookTime;

    public ContainerMachineFurnace(EntityPlayer player, TileEntity tileEntity)
    {
        super();
        this.tileFurnace = (TileEntityMachineFurnace) tileEntity;
        this.upItems = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.downItems = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotItemHandler(this.upItems, 0, 56, 26)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return FurnaceRecipes.instance().getSmeltingResult(stack) != null;
            }
        });

        this.addSlotToContainer(new SlotItemHandler(this.downItems, 0, 115, 34)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });

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
    public boolean canInteractWith(EntityPlayer player)
    {
        return player.getDistanceSq(this.tileFurnace.getPos()) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        Slot slot = this.inventorySlots.get(index);
        ItemStack oldItemstack = null;

        if (slot != null && slot.getHasStack())
        {
            ItemStack newItemstack = slot.getStack();
            oldItemstack = newItemstack.copy();

            Boolean isMerged = false;
            if (index == 0 || index == 1)
            {
                isMerged = this.mergeItemStack(newItemstack, 2, 38, true);
            }
            else if (index >= 2 && index < 29)
            {
                isMerged = this.mergeItemStack(newItemstack, 0, 1, false) || this.mergeItemStack(newItemstack, 29, 38, false);
            }
            else if (index >= 29 && index < 38)
            {
                isMerged = this.mergeItemStack(newItemstack, 0, 1, false) || this.mergeItemStack(newItemstack, 2, 29, false);
            }
            if (!isMerged) return null;

            if (newItemstack.getCount() <= 0) slot.putStack(null);
            else slot.onSlotChanged();

            slot.onTake(player, newItemstack);
        }
        return oldItemstack;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (IContainerListener crafter : this.listeners)
        {
            if (this.electricity != this.tileFurnace.electricity)
            {
                crafter.sendWindowProperty(this, 0, this.electricity);
            }

            if (this.cookTime != this.tileFurnace.cookTime)
            {
                crafter.sendWindowProperty(this, 1, this.cookTime);
            }

            if (this.totalCookTime != this.tileFurnace.totalCookTime)
            {
                crafter.sendWindowProperty(this, 2, this.totalCookTime);
            }
        }

        this.electricity = this.tileFurnace.electricity;
        this.cookTime = this.tileFurnace.cookTime;
        this.totalCookTime = this.tileFurnace.totalCookTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        super.updateProgressBar(id, data);

        switch (id)
        {
        case 0:
            this.tileFurnace.electricity = data;
            break;
        case 1:
            this.tileFurnace.cookTime = data;
            break;
        case 2:
            this.tileFurnace.totalCookTime = data;
            break;
        default:
            break;
        }
    }
}
