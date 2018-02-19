package io.github.dawncraft.entity.player;

import com.google.common.collect.Maps;

import io.github.dawncraft.network.MessageSpellCooldown;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.Skill;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpellCooldownTracker
{
    private final EntityPlayer player;
    private final Map<Skill, Integer> cooldowns = Maps.<Skill, Integer>newHashMap();

    public SpellCooldownTracker(EntityPlayer player)
    {
        this.player = player;
    }

    public boolean hasCooldown(Skill skill)
    {
        return this.getCooldown(skill) > 0.0F;
    }

    public float getCooldown(Skill skill)
    {
        if (this.cooldowns.containsKey(skill))
        {
            int cooldown = this.cooldowns.get(skill);
            return cooldown;
        }
        else
        {
            return 0.0F;
        }
    }

    public void setCooldown(Skill skill, int ticks)
    {
        this.cooldowns.put(skill, ticks);
        this.notifyOnSet(skill, ticks);
    }

    @SideOnly(Side.CLIENT)
    public void removeCooldown(Skill skill)
    {
        this.cooldowns.remove(skill);
        this.notifyOnRemove(skill);
    }

    public void notifyOnSet(Skill skill, int ticks)
    {
        if(this.player instanceof EntityPlayerMP)
        {
            NetworkLoader.instance.sendTo(new MessageSpellCooldown(skill, ticks), (EntityPlayerMP) this.player);
        }
    }

    public void notifyOnRemove(Skill skill)
    {
        if(this.player instanceof EntityPlayerMP)
        {
            NetworkLoader.instance.sendTo(new MessageSpellCooldown(skill, 0), (EntityPlayerMP) this.player);
        }
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

                if (entry.getValue() <= 0)
                {
                    this.cooldowns.remove(entry.getKey());
                    this.notifyOnRemove(entry.getKey());
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