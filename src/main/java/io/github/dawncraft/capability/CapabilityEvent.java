package io.github.dawncraft.capability;

import io.github.dawncraft.network.MessageWindowSkills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.network.MessageSpellCooldown;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.stats.AchievementLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 *
 * @author QingChenW
 */
public class CapabilityEvent
{
    public CapabilityEvent() {}
    
    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent.Entity event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            event.addCapability(IPlayerThirst.domain, new CapabilityThirst.Provider(player));
            player.getAttributeMap().registerAttribute(AttributesLoader.maxMana);
            event.addCapability(IPlayerMagic.domain, new CapabilityMagic.Provider(player));
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.entity;
            player.triggerAchievement(AchievementLoader.basic);
            if (player.hasCapability(CapabilityLoader.playerMagic, null))
            {
                IPlayerMagic playerCap = player.getCapability(CapabilityLoader.playerMagic, null);
                IStorage<IPlayerMagic> storage = CapabilityLoader.playerMagic.getStorage();

                ISkillInventory inventory = playerCap.getInventory();
                List<SkillStack> list = new ArrayList<SkillStack>();
                for(int i = 0; i < inventory.getSizeInventory(); i++)
                    list.add(inventory.getStackInSlot(i));
                NetworkLoader.instance.sendTo(new MessageWindowSkills(0, list), player);
                for(Entry<Skill, Integer> entry : playerCap.getCooldownTracker().cooldowns.entrySet())
                {
                    NetworkLoader.instance.sendTo(new MessageSpellCooldown(entry.getKey(), entry.getValue()), player);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        IPlayerThirst playerThirst = event.original.getCapability(CapabilityLoader.playerThirst, null);
        if(playerThirst.getDrinkStats() != null)
        {
            NBTTagCompound nbtThirst = (NBTTagCompound) CapabilityLoader.playerThirst.getStorage()
                    .writeNBT(CapabilityLoader.playerThirst, playerThirst, null);
            if(event.wasDeath)
            {
                playerThirst.getDrinkStats().setDrinkLevel(20);
                playerThirst.getDrinkStats().setDrinkSaturationLevel(5.0F);
            }
            CapabilityLoader.playerThirst.getStorage().readNBT(CapabilityLoader.playerThirst,
                    event.entityPlayer.getCapability(CapabilityLoader.playerThirst, null), null, nbtThirst);
        }
        
        IPlayerMagic playerMagic = event.original.getCapability(CapabilityLoader.playerMagic, null);
        NBTTagCompound nbtPlayer = (NBTTagCompound) CapabilityLoader.playerMagic.getStorage()
                .writeNBT(CapabilityLoader.playerMagic, playerMagic, null);
        if(event.wasDeath)
        {
            float mana = playerMagic.getMaxMana();
            nbtPlayer.setFloat("ManaF", mana);
            nbtPlayer.setShort("Mana", (short) Math.ceil(mana));
        }
        CapabilityLoader.playerMagic.getStorage().readNBT(CapabilityLoader.playerMagic,
                event.entityPlayer.getCapability(CapabilityLoader.playerMagic, null), null, nbtPlayer);
    }
}
