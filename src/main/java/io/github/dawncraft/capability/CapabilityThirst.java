package io.github.dawncraft.capability;

import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.entity.player.DrinkStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityThirst
{
    public static class Implementation implements IPlayerThirst
    {
        private EntityPlayer player;
        private DrinkStats drinkStats;

        public Implementation() {}

        public Implementation(EntityPlayer player)
        {
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

        @Override
        public IPlayerThirst cloneCapability(IPlayerThirst oldThirst, boolean wasDeath)
        {
            if (!wasDeath)
            {
                this.drinkStats = oldThirst.getDrinkStats();
            }
            return this;
        }
    }

    public static class Storage implements Capability.IStorage<IPlayerThirst>
    {
        @Override
        public NBTBase writeNBT(Capability<IPlayerThirst> capability, IPlayerThirst instance, EnumFacing facing)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            instance.getDrinkStats().writeNBT(tagCompound);
            return tagCompound;
        }

        @Override
        public void readNBT(Capability<IPlayerThirst> capability, IPlayerThirst instance, EnumFacing facing, NBTBase nbtBase)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) nbtBase;
            instance.getDrinkStats().readNBT(tagCompound);
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        private IPlayerThirst playerThirst;
        private IStorage<IPlayerThirst> storage;

        public Provider(EntityPlayer player)
        {
            this.playerThirst = new Implementation(player);
            this.storage = CapabilityLoader.PLAYER_THIRST.getStorage();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.PLAYER_THIRST.equals(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (this.hasCapability(capability, facing))
            {
                T result = (T) this.playerThirst;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) this.storage.writeNBT(CapabilityLoader.PLAYER_THIRST, this.playerThirst, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound tagCompound)
        {
            this.storage.readNBT(CapabilityLoader.PLAYER_THIRST, this.playerThirst, null, tagCompound);
        }
    }
}
