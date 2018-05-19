package io.github.dawncraft.entity.player;

import com.google.common.collect.Maps;

import io.github.dawncraft.skill.Skill;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpellCooldownTracker
{
    private int publicCooldownCount;
    public Map<Skill, Integer> cooldowns = Maps.<Skill, Integer>newHashMap();

    // 因为冷却是倒计时,不需要再减一下了
    public int getPublicCooldownCount()
    {
        return this.publicCooldownCount;
    }
    
    public void setPublicCooldownCount(int count)
    {
        this.publicCooldownCount = count;
    }
    
    public boolean hasCooldown(Skill skill)
    {
        return this.getCooldown(skill) > 0;
    }
    
    public int getCooldown(Skill skill)
    {
        if (this.cooldowns.containsKey(skill))
        {
            int cooldown = this.cooldowns.get(skill);
            return cooldown;
        }
        else
        {
            return 0;
        }
    }
    
    public void setCooldown(Skill skill, int ticks)
    {
        this.cooldowns.put(skill, ticks);
    }
    
    @SideOnly(Side.CLIENT)
    public void removeCooldown(Skill skill)
    {
        this.cooldowns.remove(skill);
    }
    
    public void tick()
    {
        if(this.publicCooldownCount > 0) this.publicCooldownCount--;
        
        if (!this.cooldowns.isEmpty())
        {
            for(Entry<Skill, Integer> entry : this.cooldowns.entrySet())
            {
                if (entry.getValue() > 0)
                {
                    entry.setValue(entry.getValue() - 1);
                }
                else
                {
                    this.cooldowns.remove(entry.getKey());
                }
            }
        }
    }

    public NBTTagList writeToNBT(NBTTagList tagList)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("PublicCooldown", this.publicCooldownCount);
        tagList.appendTag(tagCompound);

        for(Entry<Skill, Integer> entry : this.cooldowns.entrySet())
        {
            if (entry.getValue() > 0)
            {
                NBTTagCompound tagCompound2 = new NBTTagCompound();
                tagCompound2.setString("Id", entry.getKey().getRegistryName());
                tagCompound2.setInteger("Ticks", entry.getValue());
                tagList.appendTag(tagCompound2);
            }
        }
        return tagList;
    }

    public void readFromNBT(NBTTagList tagList)
    {
        NBTTagCompound tagCompound = tagList.getCompoundTagAt(0);
        this.publicCooldownCount = tagCompound.getInteger("PublicCooldown");

        this.cooldowns.clear();
        
        for (int i = 1; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound2 = tagList.getCompoundTagAt(i);
            Skill skill = Skill.getByNameOrId(tagCompound2.getString("Id"));
            int ticks = tagCompound2.getInteger("Ticks");
            
            if (skill != null)
            {
                this.setCooldown(skill, ticks);
            }
        }
    }
}