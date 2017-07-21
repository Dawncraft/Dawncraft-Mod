package io.github.dawncraft.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * @author QingChenW
 *
 */
public class CapabilityMana
{
    public static class Implementation implements IMana
    {
        private int mana = 20;

        @Override
        public void setMana(int mana)
        {
            this.mana = mana;
        }

        @Override
        public int getMana()
        {
            return this.mana;
        }
        
        @Override
        public void replenish()
        {
            this.mana = 20;
        }
    }

    public static class Storage implements Capability.IStorage<IMana>
    {
        @Override
        public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side)
        {
            NBTTagCompound compound = new NBTTagCompound();
            int mana = instance.getMana();
            compound.setInteger("mana", mana);
            return compound;
        }
        
        @Override
        public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            int mana = compound.getInteger("mana");
            instance.setMana(mana);
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        private IMana mana = new Implementation();
        private IStorage<IMana> storage = CapabilityLoader.mana.getStorage();
        
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.mana.equals(capability);
        }
        
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (CapabilityLoader.mana.equals(capability))
            {
                T result = (T) this.mana;
                return result;
            }
            return null;
        }
        
        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) this.storage.writeNBT(CapabilityLoader.mana, this.mana, null);
        }
        
        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            this.storage.readNBT(CapabilityLoader.mana, this.mana, null, compound);
        }
    }
}
