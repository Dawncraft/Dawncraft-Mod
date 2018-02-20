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
    public Map<Skill, Integer> cooldowns = Maps.<Skill, Integer>newHashMap();

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
        for(Entry<Skill, Integer> entry : this.cooldowns.entrySet())
        {
            if (entry.getValue() > 0)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setString("Id", entry.getKey().getRegistryName());
                tagCompound.setInteger("Ticks", entry.getValue());
                tagList.appendTag(tagCompound);
            }
        }
        return tagList;
    }
    
    public void readFromNBT(NBTTagList tagList)
    {
        this.cooldowns.clear();

        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            Skill skill = Skill.getByNameOrId(tagCompound.getString("Id"));
            int ticks = tagCompound.getInteger("Ticks");

            if (skill != null)
            {
                this.setCooldown(skill, ticks);
            }
        }
    }
}