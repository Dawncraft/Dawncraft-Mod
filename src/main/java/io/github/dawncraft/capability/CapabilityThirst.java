package io.github.dawncraft.capability;

import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.entity.player.DrinkStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityThirst
{
    public static class Implementation implements IThirst
    {
        private EntityPlayer player;
        private DrinkStats drinkStats;

        public Implementation(EntityPlayer player)
        {
            super();
            this.player = player;
            this.drinkStats = new DrinkStats();
        }
        
        @Override
        public DrinkStats getDrinkStats()
        {
            return this.drinkStats;
        }
        
        @Override
        public boolean canDrink(boolean ignoreThirst)
        {
            return (!ConfigLoader.isThirstEnabled || ignoreThirst || this.drinkStats.needDrink()) && !this.player.capabilities.disableDamage;
        }
    }

    public static class Storage implements Capability.IStorage<IThirst>
    {
        @Override
        public NBTBase writeNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side)
        {
            NBTTagCompound compound = new NBTTagCompound();
            instance.getDrinkStats().writeNBT(compound);
            return compound;
        }
        
        @Override
        public void readNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.getDrinkStats().readNBT(compound);
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        private IThirst thirst;
        private IStorage<IThirst> storage;
        
        public Provider(EntityPlayer player)
        {
            this.thirst = new Implementation(player);
            this.storage = CapabilityLoader.thirst.getStorage();
        }
        
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.thirst.equals(capability);
        }
        
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (this.hasCapability(capability, facing))
            {
                T result = (T) this.thirst;
                return result;
            }
            return null;
        }
        
        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) this.storage.writeNBT(CapabilityLoader.thirst, this.thirst, null);
        }
        
        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            this.storage.readNBT(CapabilityLoader.thirst, this.thirst, null, compound);
        }
    }
}
