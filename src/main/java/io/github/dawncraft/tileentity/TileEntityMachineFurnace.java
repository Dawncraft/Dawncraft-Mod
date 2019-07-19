package io.github.dawncraft.tileentity;

import io.github.dawncraft.block.BlockMachineFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.TextComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITextComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 *
 * @author QingChenW
 */
public class TileEntityMachineFurnace extends TileEntity implements ITickable
{
    public ItemStackHandler upItemStack = new ItemStackHandler();
    public ItemStackHandler downItemStack = new ItemStackHandler();
    public final int Max_Electricity = 32;
    public int electricity;
    public int cookTime;
    public int totalCookTime;
    
    public ITextComponent getDisplayName()
    {
        String name = "container.machineFurnace";
        return new TextComponentTranslation(name, new Object[0]);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            T result = (T) (facing == EnumFacing.DOWN ? this.downItemStack : this.upItemStack);
            return result;
        }
        return super.getCapability(capability, facing);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.upItemStack.deserializeNBT(compound.getCompoundTag("UpInventory"));
        this.downItemStack.deserializeNBT(compound.getCompoundTag("DownInventory"));
        this.electricity = compound.getShort("Electricity");
        this.cookTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("UpInventory", this.upItemStack.serializeNBT());
        compound.setTag("DownInventory", this.downItemStack.serializeNBT());
        compound.setShort("Electricity", (short) this.electricity);
        compound.setShort("CookTime", (short) this.cookTime);
        compound.setShort("CookTimeTotal", (short) this.totalCookTime);
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
    
    @Override
    public void update()
    {
        boolean wasWorking = this.isWorking();

        //        if (wasWorking) --this.electricity;

        if (!this.worldObj.isRemote)
        {
            if (this.isWorking())
            {
                ++this.cookTime;

                if (this.cookTime == this.totalCookTime)
                {
                    this.markDirty();
                    this.cookTime = 0;
                    this.totalCookTime = 200;// Should use void if need.
                    ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(this.upItemStack.extractItem(0, 1, false));
                    this.downItemStack.insertItem(0, itemStack, false);
                }
            }
            else if (!this.hasElectricity() && this.cookTime > 0)
            {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }
            else
            {
                this.cookTime = 0;
            }

            if (wasWorking != this.isWorking())
            {
                this.markDirty();
                BlockMachineFurnace.setBlockState(this.isWorking(), this.worldObj, this.pos);
            }
        }
    }
    
    public boolean isWorking()
    {
        return this.hasElectricity() && this.canSmelt();
    }

    public boolean hasElectricity()
    {
        //        return this.electricity > 0;
        return true;
    }
    
    public boolean canSmelt()
    {
        ItemStack itemStack = this.upItemStack.getStackInSlot(0);
        if (itemStack == null)
        {
            return false;
        }
        else
        {
            itemStack = FurnaceRecipes.instance().getSmeltingResult(itemStack);
            if (itemStack == null) return false;
            ItemStack itemStack2 = this.downItemStack.getStackInSlot(0);
            if (itemStack2 == null) return true;
            if (!itemStack2.isItemEqual(itemStack)) return false;
            int result = itemStack2.stackSize + itemStack.stackSize;
            return result <= 64 && result <= itemStack2.getMaxStackSize();
        }
    }
}
