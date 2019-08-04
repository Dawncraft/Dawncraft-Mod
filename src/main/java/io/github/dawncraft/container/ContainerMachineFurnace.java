package io.github.dawncraft.container;

import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
public class ContainerMachineFurnace extends Container
{
    private final TileEntityMachineFurnace tileFurnace;
    private int energy;
    private int cookTime;
    private int totalCookTime;

    public ContainerMachineFurnace(EntityPlayer player, TileEntity tileEntity)
    {
        this.tileFurnace = (TileEntityMachineFurnace) tileEntity;

        IItemHandler upItemStack = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler downItemStack = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.addSlotToContainer(new SlotItemHandler(upItemStack, 0, 56, 26));
        this.addSlotToContainer(new SlotItemHandler(downItemStack, 0, 115, 34)
        {
            private int removeCount;

            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }

            @Override
            public void onSlotChange(ItemStack stack1, ItemStack stack2)
            {
                int i = stack2.getCount() - stack1.getCount();

                if (i > 0)
                {
                    this.onCrafting(stack2, i);
                }
            }

            @Override
            public ItemStack decrStackSize(int amount)
            {
                if (this.getHasStack())
                {
                    this.removeCount += Math.min(amount, this.getStack().getCount());
                }
                return super.decrStackSize(amount);
            }

            @Override
            protected void onCrafting(ItemStack stack, int amount)
            {
                this.removeCount += amount;
                this.onCrafting(stack);
            }

            @Override
            public ItemStack onTake(EntityPlayer player, ItemStack stack)
            {
                super.onTake(player, stack);
                stack.onCrafting(player.world, player, this.removeCount);

                if (!player.world.isRemote)
                {
                    int i = this.removeCount;
                    float f = FurnaceRecipes.instance().getSmeltingExperience(stack);

                    if (f == 0.0F)
                    {
                        i = 0;
                    }
                    else if (f < 1.0F)
                    {
                        int j = MathHelper.floor(i * f);

                        if (j < MathHelper.ceil(i * f) && Math.random() < i * f - j)
                        {
                            ++j;
                        }

                        i = j;
                    }

                    while (i > 0)
                    {
                        int k = EntityXPOrb.getXPSplit(i);
                        i -= k;
                        player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, k));
                    }
                }

                this.removeCount = 0;
                FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack);
                return stack;
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
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.energy);
        listener.sendWindowProperty(this, 1, this.cookTime);
        listener.sendWindowProperty(this, 2, this.totalCookTime);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (IContainerListener listeners : this.listeners)
        {
            if (this.energy != this.tileFurnace.getEnergyStored())
            {
                listeners.sendWindowProperty(this, 0, this.energy);
            }
            if (this.cookTime != this.tileFurnace.cookTime)
            {
                listeners.sendWindowProperty(this, 1, this.cookTime);
            }
            if (this.totalCookTime != this.tileFurnace.totalCookTime)
            {
                listeners.sendWindowProperty(this, 2, this.totalCookTime);
            }
        }

        this.energy = this.tileFurnace.getEnergyStored();
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
            this.tileFurnace.setEnergyStored(data);
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
            if (!isMerged) return ItemStack.EMPTY;

            if (newItemstack.getCount() <= 0) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            slot.onTake(player, newItemstack);
        }
        return oldItemstack;
    }
}
