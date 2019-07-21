package io.github.dawncraft.container;

import io.github.dawncraft.tileentity.TileEntityEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
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
public class ContainerEnergyGenerator extends Container
{
    public final TileEntityEnergyGenerator tileGenerator;
    public IItemHandler fuelItems;
    public int generatorBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int electricity = 0;

    public ContainerEnergyGenerator(EntityPlayer player, TileEntity tileEntity)
    {
        super();
        this.tileGenerator = (TileEntityEnergyGenerator) tileEntity;
        this.fuelItems = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        this.addSlotToContainer(new SlotItemHandler(this.fuelItems, 0, 55, 46)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack);
            }

            @Override
            public int getItemStackLimit(ItemStack stack)
            {
                return SlotFurnaceFuel.isBucket(stack) ? 1 : super.getItemStackLimit(stack);
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
        return player.getDistanceSq(this.tileGenerator.getPos()) <= 64.0D;
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
            if (index == 0)
            {
                isMerged = this.mergeItemStack(newItemstack, 1, 37, true);
            }
            else if (index >= 1 && index < 28)
            {
                isMerged = this.mergeItemStack(newItemstack, 0, 1, false) || this.mergeItemStack(newItemstack, 28, 37, false);
            }
            else if (index >= 28 && index < 37)
            {
                isMerged = this.mergeItemStack(newItemstack, 0, 1, false) || this.mergeItemStack(newItemstack, 1, 28, false);
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
            if (this.generatorBurnTime != this.tileGenerator.generatorBurnTime)
            {
                crafter.sendWindowProperty(this, 0, this.generatorBurnTime);
            }

            if (this.currentItemBurnTime != this.tileGenerator.currentItemBurnTime)
            {
                crafter.sendWindowProperty(this, 1, this.currentItemBurnTime);
            }

            if (this.electricity != this.tileGenerator.electricity)
            {
                crafter.sendWindowProperty(this, 2, this.electricity);
            }
        }

        this.generatorBurnTime = this.tileGenerator.generatorBurnTime;
        this.currentItemBurnTime = this.tileGenerator.currentItemBurnTime;
        this.electricity = this.tileGenerator.electricity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        super.updateProgressBar(id, data);

        switch (id)
        {
        case 0:
            this.tileGenerator.generatorBurnTime = data;
            break;
        case 1:
            this.tileGenerator.currentItemBurnTime = data;
            break;
        case 2:
            this.tileGenerator.electricity = data;
            break;
        default:
            break;
        }
    }
}
