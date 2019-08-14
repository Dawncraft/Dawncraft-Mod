package io.github.dawncraft.api.tileentity;

import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorage implements IEnergyStorage
{
    protected int energy;
    protected int capacity;

    public EnergyStorage(int capacity)
    {
        this(capacity, 0);
    }

    public EnergyStorage(int capacity, int energy)
    {
        this.capacity = Math.max(0, capacity);
        this.energy = Math.max(0, Math.min(energy, capacity));
    }

    @Override
    public boolean canReceive()
    {
        return this.getEnergyStored() < this.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract()
    {
        return this.getEnergyStored() > 0;
    }

    @Override
    public int receiveEnergy(int receive, boolean simulate)
    {
        if (!this.canReceive())
            return 0;

        int received = Math.min(this.capacity - this.energy, Math.max(0, receive));
        if (!simulate)
            this.energy += received;
        return received;
    }

    @Override
    public int extractEnergy(int extract, boolean simulate)
    {
        if (!this.canExtract())
            return 0;

        int extracted = Math.min(this.energy, Math.min(0, extract));
        if (!simulate)
            this.energy -= extracted;
        return extracted;
    }

    @Override
    public int getEnergyStored()
    {
        return this.energy;
    }

    public void setEnergyStored(int energy)
    {
        this.energy = energy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return this.capacity;
    }
}
