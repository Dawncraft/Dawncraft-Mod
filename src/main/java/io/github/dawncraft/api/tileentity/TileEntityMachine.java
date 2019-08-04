package io.github.dawncraft.api.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * The basic machine tileentity, you can make your own machine te by extending it.
 *
 * @author QingChenW
 */
public abstract class TileEntityMachine extends TileEntity implements IEnergyStorage, IWorldNameable, ITickable
{
    private String customName;
    private EnergyStorage energy = new EnergyStorage(this.getMaxEnergyStored());

    public abstract String getTranslationKey();

    @Override
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String name)
    {
        this.customName = name;
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : this.getTranslationKey();
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public boolean canReceive()
    {
        return this.energy.canReceive();
    }

    @Override
    public boolean canExtract()
    {
        return this.energy.canExtract();
    }

    @Override
    public int receiveEnergy(int receive, boolean simulate)
    {
        return this.energy.receiveEnergy(receive, simulate);
    }

    @Override
    public int extractEnergy(int extract, boolean simulate)
    {
        return this.energy.extractEnergy(extract, simulate);
    }

    @Override
    public int getEnergyStored()
    {
        return this.energy.getEnergyStored();
    }

    public void setEnergyStored(int energy)
    {
        this.energy.setEnergyStored(energy);
    }

    @Override
    public abstract int getMaxEnergyStored();

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability.equals(CapabilityEnergy.ENERGY))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (this.hasCapability(capability, facing))
        {
            return CapabilityEnergy.ENERGY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
        this.energy = new EnergyStorage(this.getMaxEnergyStored(), compound.getInteger("Electricity"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }
        compound.setInteger("Electricity", this.energy.getEnergyStored());
        return compound;
    }
}
