package io.github.dawncraft.entity.player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.skill.Skill;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;

public class SpellCooldownTracker
{
    private int tick;
    /** global cooldown's expire tick */
    private int globalCooldownTick;
    protected Map<Skill, Cooldown> cooldowns = new HashMap<>();

    public static int getTotalGlobalCooldown()
    {
        return ConfigLoader.globalCooldownTick;
    }

    public int getGlobalCooldown()
    {
        int tick = this.globalCooldownTick - this.tick;
        return Math.max(tick, 0);
    }

    public float getGlobalCooldownPercent(float partialTick)
    {
        float current = this.getGlobalCooldown() - partialTick;
        float total = getTotalGlobalCooldown();
        return MathHelper.clamp(current / total, 0.0F, 1.0F);
    }

    public boolean isGlobalCooldown()
    {
        return this.getGlobalCooldown() > 0;
    }

    public void setGlobalCooldown(int tick)
    {
        this.globalCooldownTick = this.tick + tick;
        this.notifyOnSet(tick);
    }

    public int getCooldown(Skill skill)
    {
        Cooldown cooldown = this.cooldowns.get(skill);
        if (cooldown != null)
        {
            int tick = cooldown.expireTick - this.tick;
            return Math.max(tick, 0);
        }
        return 0;
    }

    public float getCooldownPercent(Skill skill, float partialTick)
    {
        Cooldown cooldown = this.cooldowns.get(skill);
        if (cooldown != null)
        {
            float current = this.getCooldown(skill) - partialTick;
            float total = cooldown.expireTick - cooldown.createTick;
            return MathHelper.clamp(current / total, 0.0F, 1.0F);
        }
        return 0.0F;
    }

    public boolean hasCooldown(Skill skill)
    {
        return this.getCooldown(skill) > 0;
    }

    public void setCooldown(Skill skill, int tick)
    {
        this.cooldowns.put(skill, new Cooldown(this.tick, this.tick + tick));
        this.notifyOnSet(skill, tick);
    }

    public void removeCooldown(Skill skill)
    {
        this.cooldowns.remove(skill);
        this.notifyOnRemove(skill);
    }

    public void tick()
    {
        ++this.tick;

        if (!this.cooldowns.isEmpty())
        {
            Iterator<Entry<Skill, Cooldown>> iterator = this.cooldowns.entrySet().iterator();

            while (iterator.hasNext())
            {
                Entry<Skill, Cooldown> entry = iterator.next();

                if (entry.getValue().expireTick <= this.tick)
                {
                    iterator.remove();
                }
            }
        }
    }

    public void notifyOnSet(int tick) {}

    public void notifyOnSet(Skill skill, int tick) {}

    public void notifyOnRemove(Skill skill) {}

    public void sendAll() {}

    public NBTTagList writeToNBT(NBTTagList tagList)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("Global", this.getGlobalCooldown());
        tagList.appendTag(tagCompound);

        for (Entry<Skill, Cooldown> entry : this.cooldowns.entrySet())
        {
            NBTTagCompound tagCompound2 = new NBTTagCompound();
            tagCompound2.setString("Id", entry.getKey().getRegistryName().toString());
            tagCompound2.setInteger("Tick", this.getCooldown(entry.getKey()));
            tagList.appendTag(tagCompound2);
        }

        return tagList;
    }

    public void readFromNBT(NBTTagList tagList)
    {
        this.tick = 0;

        NBTTagCompound tagCompound = tagList.getCompoundTagAt(0);
        this.globalCooldownTick = tagCompound.getInteger("Global");

        this.cooldowns.clear();

        for (int i = 1; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tagCompound2 = tagList.getCompoundTagAt(i);
            Skill skill = Skill.getByNameOrId(tagCompound2.getString("Id"));
            int tick = tagCompound2.getInteger("Tick");

            if (skill != null)
            {
                this.cooldowns.put(skill, new Cooldown(0, tick));
            }
        }
    }

    protected class Cooldown
    {
        final int createTick;
        final int expireTick;

        Cooldown(int createTick, int expireTick)
        {
            this.createTick = createTick;
            this.expireTick = expireTick;
        }
    }
}