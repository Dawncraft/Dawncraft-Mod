package io.github.dawncraft.tileentity;

import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.api.tileentity.TileEntityMachine;
import io.github.dawncraft.block.BlockEnergyGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 *
 *
 * @author QingChenW
 */
public class TileEntityEnergyGenerator extends TileEntityMachine
{
    public BlockEnergyGenerator.EnumGeneratorType generatorType;
    public ItemStackHandler fuelItemStack = new ItemStackHandler();
    public int generatorBurnTime;
    public int currentItemBurnTime;

    @Override
    public String getTranslationKey()
    {
        return "container." + this.getWorld().getBlockState(this.getPos()).getBlock().getTranslationKey();
    }

    @Override
    public int getMaxEnergyStored()
    {
        return 12800;
    }

    @Override
    public void onLoad()
    {
        /*        Block block = this.getWorld().getBlockState(this.getPos()).getBlock();
        if(block instanceof BlockEnergyGenerator)
        {
            this.generatorType = ((BlockEnergyGenerator) block).type;
        }*/
    }

    @Override
    public void update()
    {
        boolean wasWorking = this.isWorking();

        if (wasWorking) --this.generatorBurnTime;

        if (!this.world.isRemote)
        {
            if (!this.isWorking() && this.canWork())
            {
                this.currentItemBurnTime = this.generatorBurnTime = TileEntityFurnace.getItemBurnTime(this.fuelItemStack.getStackInSlot(0));

                if(this.isWorking())
                {
                    this.markDirty();
                    this.fuelItemStack.extractItem(0, 1, false);
                }
            }

            if (this.isWorking() && this.canWork())
            {
                this.receiveEnergy(1, false);
            }

            if (wasWorking != this.isWorking())
            {
                this.markDirty();
                BlockMachine.setBlockState(this.isWorking(), this.world, this.pos);
            }
        }
    }

    public boolean isWorking()
    {
        return this.generatorBurnTime > 0;
    }

    private boolean canWork()
    {
        return this.canReceive();
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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.fuelItemStack);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fuelItemStack.deserializeNBT(compound.getCompoundTag("FuelInventory"));
        this.generatorBurnTime = compound.getShort("BurnTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.fuelItemStack.getStackInSlot(0));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("FuelInventory", this.fuelItemStack.serializeNBT());
        compound.setShort("BurnTime", (short) this.generatorBurnTime);
        return compound;
    }
}
