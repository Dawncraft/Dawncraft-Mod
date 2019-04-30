package io.github.dawncraft.server;

import net.minecraft.entity.player.EntityPlayerMP;

import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.network.MessageSpellCooldown;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.Skill;

public class SpellCooldownTrackerServer extends SpellCooldownTracker
{
    private EntityPlayerMP player;

    public SpellCooldownTrackerServer(EntityPlayerMP player)
    {
        this.player = player;
    }

    @Override
    public void notifyOnSet(int tick)
    {
        NetworkLoader.instance.sendTo(new MessageSpellCooldown(tick), this.player);
    }
    
    @Override
    public void notifyOnSet(Skill skill, int tick)
    {
        NetworkLoader.instance.sendTo(new MessageSpellCooldown(skill, tick), this.player);
    }

    @Override
    public void notifyOnRemove(Skill skill)
    {
        NetworkLoader.instance.sendTo(new MessageSpellCooldown(skill, 0), this.player);
    }

    @Override
    public void sendAll()
    {
        this.notifyOnSet(this.getGlobalCooldown());
        for (Skill skill : this.cooldowns.keySet())
        {
            this.notifyOnSet(skill, this.getCooldown(skill));
        }
    }
}
