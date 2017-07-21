package io.github.dawncraft.tileentity;

import io.github.dawncraft.block.BlockEnergyGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author QingChenW
 *
 */
public class TileEntityEnergyHeatGen extends TileEntity implements ITickable
{
    protected ItemStackHandler fuelItemStack = new ItemStackHandler();
    protected int burnTime = 0;
    protected int currentItemBurnTime = 0;
    protected int electricity = 0;
    protected final int Max_Electricity = 12800;

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
            T result = (T) (facing == EnumFacing.DOWN ? this.fuelItemStack : null);
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fuelItemStack.deserializeNBT(compound.getCompoundTag("FuelInventory"));
        this.burnTime = compound.getInteger("BurnTime");
        this.electricity = compound.getInteger("Electricity");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("FuelInventory", this.fuelItemStack.serializeNBT());
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("Electricity", this.electricity);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    // TODO: 热能发电机te待重写
    @Override
    public void update()
    {
        if (!this.worldObj.isRemote)
        {
            boolean wasBurning = this.isWorking();
            boolean isBurning = false;

            if(wasBurning) --this.burnTime;

            if(!this.isWorking() && this.canWork())
            {
                this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.fuelItemStack.extractItem(0, 1, true));

                if(this.isWorking())
                {
                    isBurning = true;
                    this.fuelItemStack.extractItem(0, 1, false);
                }

                if(this.isWorking() && this.canWork())
                {
                    isBurning = true;
                    if(++this.electricity < this.Max_Electricity)
                    {
                        ++this.electricity;
                    }
                }
            }

            if (wasBurning != this.isWorking())
            {
                isBurning = true;
                BlockEnergyGenerator.setBlockState(this.isWorking(), this.worldObj, this.pos);
            }

            if (isBurning)
            {
                this.markDirty();
            }
        }
    }

    public boolean isWorking()
    {
        return this.burnTime > 0;
    }

    private boolean canWork()
    {
        return this.electricity < this.Max_Electricity;
    }

    public static boolean isItemFuel(ItemStack ItemStack)
    {
        return getItemBurnTime(ItemStack) > 0;
    }

    public static int getItemBurnTime(ItemStack ItemStack)
    {
        if(ItemStack == null) return 0;
        return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(ItemStack);
    }

    /*        else
        {
            Item item = ItemStack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block.getMaterial() == Material.wood) { return 300; }

                if (block == Blocks.coal_block) { return 16000; }
            }

            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Items.blaze_rod) return 2400;
     */
}
