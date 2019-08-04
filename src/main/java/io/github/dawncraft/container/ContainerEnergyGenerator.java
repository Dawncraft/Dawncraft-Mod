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
 *
 * @author QingChenW
 */
public class ContainerEnergyGenerator extends Container
{
    private final TileEntityEnergyGenerator tileGenerator;
    private int generatorBurnTime = 0;
    private int currentItemBurnTime = 0;
    private int energy = 0;

    public ContainerEnergyGenerator(EntityPlayer player, TileEntity tileEntity)
    {
        this.tileGenerator = (TileEntityEnergyGenerator) tileEntity;

        IItemHandler fuelItemStack = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.addSlotToContainer(new SlotItemHandler(fuelItemStack, 0, 55, 46)
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
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.generatorBurnTime);
        listener.sendWindowProperty(this, 1, this.currentItemBurnTime);
        listener.sendWindowProperty(this, 2, this.energy);
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

            if (this.energy != this.tileGenerator.getEnergyStored())
            {
                crafter.sendWindowProperty(this, 2, this.energy);
            }
        }

        this.generatorBurnTime = this.tileGenerator.generatorBurnTime;
        this.currentItemBurnTime = this.tileGenerator.currentItemBurnTime;
        this.energy = this.tileGenerator.getEnergyStored();
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
            this.tileGenerator.setEnergyStored(data);;
            break;
        default:
            break;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        Slot slot = this.inventorySlots.get(index);
        ItemStack oldItemstack = ItemStack.EMPTY;

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
            if (!isMerged) return ItemStack.EMPTY;

            if (newItemstack.getCount() <= 0) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            slot.onTake(player, newItemstack);
        }
        return oldItemstack;
    }
}
