package io.github.dawncraft.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.container.SkillInventoryPlayer;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.entity.magicile.EnumSpellAction;
import io.github.dawncraft.skill.EnumSpellResult;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.talent.Talent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * @author QingChenW
 *
 */
public class CapabilityMagic
{
    public static class Implementation implements IMagic
    {
        private float mana;
        private EnumSpellAction spellAction;
        private boolean isSpellCanceled;
        private int currentSkill;
        private SkillStack skillInSpell;
        private int skillInSpellCount;
        private int publicCooldownCount;
        private ISkillInventory inventory;
        private Map<Talent, Integer> talents;

        public Implementation(EntityPlayer player)
        {
            this.mana = this.getMaxMana();
            this.spellAction = EnumSpellAction.NONE;
            this.inventory = new SkillInventoryPlayer(player);
            this.talents = new HashMap<Talent, Integer>();
        }

        @Override
        public float getMana()
        {
            return this.mana;
        }

        @Override
        public float getMaxMana()
        {
            // TODO 获取最大魔法值
            return (float) AttributesLoader.maxMana.getDefaultValue();
        }
        
        @Override
        public void setMana(float amount)
        {
            if(amount < 0.0F) amount = 0.0F;
            if(amount > this.getMaxMana()) amount = this.getMaxMana();
            this.mana = amount;
        }

        @Override
        public void recover(float recoverAmount)
        {
            if (recoverAmount <= 0) return;
            float mana = this.getMana();
            if (mana < 0.0F) mana = 0.0F;
            mana += recoverAmount;
            if (mana > this.getMaxMana()) mana = this.getMaxMana();
            this.setMana(mana);
        }
        
        @Override
        public void reduce(float reduceAmount)
        {
            if (reduceAmount <= 0) return;
            float mana = this.getMana();
            if (mana > this.getMaxMana()) mana = this.getMaxMana();
            mana -= reduceAmount;
            if (mana < 0.0F) mana = 0.0F;
            this.setMana(mana);
        }
        
        @Override
        public void replenish()
        {
            this.mana = this.getMaxMana();
        }
        
        @Override
        public EnumSpellAction getSpellAction()
        {
            return this.spellAction;
        }
        
        @Override
        public void setSpellAction(EnumSpellAction action)
        {
            this.spellAction = action;
        }
        
        @Override
        public boolean isSpellCanceled()
        {
            return this.isSpellCanceled;
        }

        @Override
        public void cancelSpelling(EnumSpellResult reason)
        {
            this.isSpellCanceled = true;
        }

        @Override
        public int getSpellIndex()
        {
            return this.currentSkill;
        }

        @Override
        public void setSpellIndex(int index)
        {
            this.currentSkill = index;
        }
        
        @Override
        public SkillStack getSkillInSpell()
        {
            return this.skillInSpell;
        }
        
        @Override
        public void setSkillInSpell(SkillStack stack)
        {
            this.skillInSpell = stack;
        }
        
        @Override
        public void clearSkillInSpell()
        {
            this.spellAction = EnumSpellAction.NONE;
            this.isSpellCanceled = false;
            this.currentSkill = -1;
            this.skillInSpell = null;
            this.skillInSpellCount = 0;
        }
        
        @Override
        public int getSkillInSpellCount()
        {
            return this.skillInSpellCount;
        }
        
        @Override
        public int getSkillInSpellDuration()
        {
            if(this.spellAction == EnumSpellAction.PREPARE)
            {
                return this.skillInSpell.getTotalPrepare() - this.skillInSpellCount;
            }
            else if(this.spellAction == EnumSpellAction.SPELLING)
            {
                return this.skillInSpell.getMaxDuration() - this.skillInSpellCount;
            }
            return 0;
        }
        
        @Override
        public void setSkillInSpellCount(int count)
        {
            this.skillInSpellCount = count;
        }
        
        @Override
        public int getPublicCooldownCount()
        {
            return this.publicCooldownCount;
        }
        
        @Override
        public void setPublicCooldownCount(int count)
        {
            this.publicCooldownCount = count;
        }

        @Override
        public void setInventory(ISkillInventory inventory)
        {
            this.inventory = inventory;
        }

        @Override
        public ISkillInventory getInventory()
        {
            return this.inventory;
        }
        
        @Override
        public void setTalent(Talent talent, int level)
        {
            this.talents.put(talent, level);
        }
        
        @Override
        public int getTalentLevel(Talent talent)
        {
            return this.talents.get(talent);
        }

        @Deprecated
        @Override
        public Set<Talent> getTalents()
        {
            return this.talents.keySet();
        }
    }

    public static class Storage implements Capability.IStorage<IMagic>
    {
        @Override
        public NBTBase writeNBT(Capability<IMagic> capability, IMagic instance, EnumFacing side)
        {
            NBTTagCompound compound = new NBTTagCompound();
            float mana = instance.getMana();
            compound.setFloat("ManaF", mana);
            compound.setShort("Mana", (short) Math.ceil(mana));

            int cooldown = instance.getPublicCooldownCount();
            compound.setInteger("Cooldown", cooldown);
            
            NBTTagList skills = new NBTTagList();
            ((SkillInventoryPlayer) instance.getInventory()).writeToNBT(skills);
            compound.setTag("SkillInventory", skills);

            NBTTagList talents = new NBTTagList();
            for (Talent talent : instance.getTalents())
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setShort(talent.getUnlocalizedName(), (short) instance.getTalentLevel(talent));
                talents.appendTag(nbt);
            }
            compound.setTag("Talents", talents);

            return compound;
        }
        
        @Override
        public void readNBT(Capability<IMagic> capability, IMagic instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound = (NBTTagCompound) nbt;

            if (compound.hasKey("ManaF", 99))
            {
                instance.setMana(compound.getFloat("ManaF"));
            }
            else
            {
                NBTBase nbtbase = compound.getTag("Mana");

                if (nbtbase == null)
                {
                    instance.setMana(instance.getMaxMana());
                }
                else if (nbtbase.getId() == 5)
                {
                    instance.setMana(((NBTTagFloat) nbtbase).getFloat());
                }
                else if (nbtbase.getId() == 2)
                {
                    instance.setMana((float)((NBTTagShort)nbtbase).getShort());
                }
            }

            instance.setPublicCooldownCount(compound.getInteger("Cooldown"));

            NBTTagList skills = compound.getTagList("SkillInventory", 9);
            ((SkillInventoryPlayer) instance.getInventory()).readFromNBT(skills);
            
            NBTTagList talents = compound.getTagList("Talents", 9);
            for (int i = 0; i < talents.tagCount(); ++i)
            {
                NBTTagCompound nbt2 = talents.getCompoundTagAt(i);
                //                instance.setTalent();
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        private IMagic magic;
        private IStorage<IMagic> storage;
        
        public Provider(EntityPlayer player)
        {
            this.magic = new Implementation(player);
            this.storage = CapabilityLoader.magic.getStorage();
        }
        
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.magic.equals(capability);
        }
        
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (this.hasCapability(capability, facing))
            {
                T result = (T) this.magic;
                return result;
            }
            return null;
        }
        
        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) this.storage.writeNBT(CapabilityLoader.magic, this.magic, null);
        }
        
        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            this.storage.readNBT(CapabilityLoader.magic, this.magic, null, compound);
        }
    }
}

