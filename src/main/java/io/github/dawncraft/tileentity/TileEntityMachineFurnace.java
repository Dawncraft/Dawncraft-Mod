package io.github.dawncraft.tileentity;

import javax.annotation.Nonnull;

import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.api.tileentity.TileEntityMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * The block entity of machine furnace
 *
 * @author QingChenW
 */
public class TileEntityMachineFurnace extends TileEntityMachine
{
    public ItemStackHandler upItemStack = new ItemStackHandler();
    public ItemStackHandler downItemStack = new ItemStackHandler()
    {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return false;
        }
    };
    public int cookTime;
    public int totalCookTime;

    @Override
    public String getTranslationKey()
    {
        return "container.machineFurnace";
    }

    @Override
    public int getMaxEnergyStored()
    {
        return 32;
    }

    @Override
    public void update()
    {
        boolean update = false;
        boolean wasWorking = this.isWorking();

        if (wasWorking) this.extractEnergy(1, false);

        if (!this.world.isRemote)
        {
            if (this.isWorking())
            {
                if (this.canSmelt())
                {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = 200;
                        this.smeltItem();
                        update = true;
                    }
                }
                else if (this.downItemStack.getStackInSlot(0).isEmpty())
                {
                    this.cookTime = 0;
                }
            }
            else if (!this.canExtract() && this.cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (wasWorking != this.isWorking())
            {
                update = true;
                BlockMachine.setBlockState(this.isWorking(), this.world, this.pos);
            }
        }

        if (update)
        {
            this.markDirty();
        }
    }

    public boolean isWorking()
    {
        return this.canExtract();
    }

    public boolean canSmelt()
    {
        ItemStack itemStack = this.downItemStack.getStackInSlot(0);
        if (itemStack.isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemStack1 = FurnaceRecipes.instance().getSmeltingResult(itemStack);
            if (itemStack1.isEmpty()) return false;
            ItemStack itemStack2 = this.upItemStack.getStackInSlot(0);
            if (itemStack2.isEmpty()) return true;
            else if (!itemStack2.isItemEqual(itemStack1)) return false;
            int result = itemStack2.getCount() + itemStack1.getCount();
            return result <= 64 && result <= itemStack1.getMaxStackSize() && result <= itemStack2.getMaxStackSize();
        }
    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemStack = this.downItemStack.extractItem(0, 1, false);
            ItemStack itemStack1 = FurnaceRecipes.instance().getSmeltingResult(itemStack);
            this.upItemStack.insertItem(0, itemStack1, false);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
        {
            ItemStackHandler result = facing != EnumFacing.DOWN ? this.upItemStack : this.downItemStack;
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(result);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.upItemStack.deserializeNBT(compound.getCompoundTag("UpInventory"));
        this.downItemStack.deserializeNBT(compound.getCompoundTag("DownInventory"));
        this.cookTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("UpInventory", this.upItemStack.serializeNBT());
        compound.setTag("DownInventory", this.downItemStack.serializeNBT());
        compound.setShort("CookTime", (short) this.cookTime);
        compound.setShort("CookTimeTotal", (short) this.totalCookTime);
        return compound;
    }
}
