package io.github.dawncraft.capability;

import io.github.dawncraft.network.MessageWindowSkills;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.stats.AchievementLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Some events that are used to attach capabilities.
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
                IPlayerMagic magic = player.getCapability(CapabilityLoader.playerMagic, null);

                ISkillInventory inventory = magic.getInventory();
                
                List<SkillStack> list = new ArrayList<SkillStack>();
                for(int i = 0; i < inventory.getInventorySize(); i++)
                    list.add(inventory.getStackInSlot(i));
                NetworkLoader.instance.sendTo(new MessageWindowSkills(0, list), player);

                magic.getCooldownTracker().synchronizeAll();
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        IPlayerThirst playerThirst = event.original.getCapability(CapabilityLoader.playerThirst, null);
        if (playerThirst.getDrinkStats() != null)
        {
            if (event.wasDeath)
            {
                playerThirst.getDrinkStats().setDrinkLevel(20);
                playerThirst.getDrinkStats().setDrinkSaturationLevel(5.0F);
            }
            NBTTagCompound nbtThirst = (NBTTagCompound) CapabilityLoader.playerThirst.getStorage()
                    .writeNBT(CapabilityLoader.playerThirst, playerThirst, null);
            CapabilityLoader.playerThirst.getStorage().readNBT(CapabilityLoader.playerThirst,
                    event.entityPlayer.getCapability(CapabilityLoader.playerThirst, null), null, nbtThirst);
        }
        
        IPlayerMagic playerMagic = event.original.getCapability(CapabilityLoader.playerMagic, null);
        if (event.wasDeath)
        {
            playerMagic.setMana(playerMagic.getMaxMana());
        }
        NBTTagCompound nbtPlayer = (NBTTagCompound) CapabilityLoader.playerMagic.getStorage()
                .writeNBT(CapabilityLoader.playerMagic, playerMagic, null);
        CapabilityLoader.playerMagic.getStorage().readNBT(CapabilityLoader.playerMagic,
                event.entityPlayer.getCapability(CapabilityLoader.playerMagic, null), null, nbtPlayer);
    }
}
