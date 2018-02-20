package io.github.dawncraft.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
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
 * IMagic 的具体实现
 *
 * @author QingChenW
 */
public class CapabilityMagic
{
    public static class Implementation implements IMagic
    {
        private EntityPlayer player;
        // 要不是Mojang那不知所以然的prevPos,还用我再写嘛= =
        // 主要用于判断玩家是否移动
        private double prevPosX;
        private double prevPosY;
        private double prevPosZ;
        
        private float mana;
        private EnumSpellResult spellAction;
        private boolean canceled;
        private int currentSkill;
        private SkillStack skillInSpell;
        private int skillInSpellCount;
        private int publicCooldownCount;
        private SpellCooldownTracker tracker;
        private ISkillInventory inventory;
        private Map<Talent, Integer> talents;
        
        public Implementation(EntityPlayer player)
        {
            this.player = player;
            this.prevPosY = this.player.posX;
            this.prevPosY = this.player.posY;
            this.prevPosZ = this.player.posZ;

            this.mana = this.getMaxMana();
            this.spellAction = EnumSpellResult.NONE;
            this.canceled = false;
            this.tracker = new SpellCooldownTracker();
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
            return (float) this.player.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue();
        }
        
        @Override
        public void setMana(float amount)
        {
            if(amount < 0.0F) amount = 0.0F;
            if(amount > this.getMaxMana()) amount = this.getMaxMana();
            this.mana = amount;
        }

        @Override
        public boolean shouldRecover()
        {
            return this.getMana() < this.getMaxMana();
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
        public EnumSpellResult getSpellAction()
        {
            return this.spellAction;
        }
        
        @Override
        public void setSpellAction(EnumSpellResult action)
        {
            this.spellAction = action;
        }
        
        @Override
        public boolean isCanceled()
        {
            return this.canceled;
        }
        
        @Override
        public void cancelSpelling()
        {
            this.canceled = true;
        }
        
        @Override
        public void setCanceled(boolean isCanceled)
        {
            this.canceled = isCanceled;
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
            this.spellAction = EnumSpellResult.NONE;
            this.canceled = false;
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
            if(this.spellAction == EnumSpellResult.PREPARING)
            {
                return this.skillInSpell.getTotalPrepare() - this.skillInSpellCount;
            }
            else if(this.spellAction == EnumSpellResult.SPELLING)
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
        public SpellCooldownTracker getCooldownTracker()
        {
            return this.tracker;
        }

        @Override
        public void setCooldownTracker(SpellCooldownTracker tracker)
        {
            this.tracker = tracker;
        }
        
        @Override
        public ISkillInventory getInventory()
        {
            return this.inventory;
        }
        
        @Override
        public void setInventory(ISkillInventory inventory)
        {
            this.inventory = inventory;
        }
        
        @Override
        public int getTalentLevel(Talent talent)
        {
            return this.talents.get(talent);
        }
        
        @Override
        public void setTalent(Talent talent, int level)
        {
            this.talents.put(talent, level);
        }
        
        @Deprecated
        @Override
        public Set<Talent> getTalents()
        {
            return this.talents.keySet();
        }
        
        // TODO 看看MC的更新是怎么做的
        @Override
        public void update()
        {
            if(this.publicCooldownCount > 0) this.publicCooldownCount--;
            if(this.skillInSpellCount > 0) this.skillInSpellCount--;
            this.getCooldownTracker().tick();
            
            for(int i = 0; i < this.inventory.getSizeInventory(); i++)
            {
                SkillStack skillstack = this.inventory.getStackInSlot(i);
                if(skillstack != null)
                    skillstack.updateAnimation(this.player.worldObj, this.player, i);
            }
            
            if(this.player.isServerWorld())
            {
                double x = this.player.posX - this.prevPosX;
                double y = this.player.posY - this.prevPosY;
                double z = this.player.posZ - this.prevPosZ;
                if (x * x + y * y + z * z > 0)
                {
                    this.cancelSpelling();
                }
            }

            this.prevPosX = this.player.posX;
            this.prevPosY = this.player.posY;
            this.prevPosZ = this.player.posZ;
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
            compound.setInteger("PublicCooldown", cooldown);
            
            NBTTagList cooldowns = new NBTTagList();
            instance.getCooldownTracker().writeToNBT(cooldowns);
            compound.setTag("Cooldowns", cooldowns);
            
            NBTTagList skills = new NBTTagList();
            ((SkillInventoryPlayer) instance.getInventory()).writeToNBT(skills);
            compound.setTag("Inventory", skills);
            
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
                    instance.setMana(((NBTTagShort)nbtbase).getShort());
                }
            }
            
            instance.setPublicCooldownCount(compound.getInteger("PublicCooldown"));
            
            NBTTagList cooldowns = compound.getTagList("Cooldowns", 10);
            instance.getCooldownTracker().readFromNBT(cooldowns);
            
            NBTTagList skills = compound.getTagList("Inventory", 10);
            ((SkillInventoryPlayer) instance.getInventory()).readFromNBT(skills);
            
            NBTTagList talents = compound.getTagList("Talents", 10);
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

