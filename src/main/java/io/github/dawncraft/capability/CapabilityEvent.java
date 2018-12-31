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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 *
 * @author QingChenW
 */
public class CapabilityEvent
{
    public CapabilityEvent(FMLInitializationEvent event) {}

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent.Entity event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            event.addCapability(IThirst.domain, new CapabilityThirst.Provider(player));
            player.getAttributeMap().registerAttribute(AttributesLoader.maxMana);
            event.addCapability(IMagic.domain, new CapabilityPlayer.Provider(player));
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.entity;
            player.triggerAchievement(AchievementLoader.basic);
            if (player.hasCapability(CapabilityLoader.magic, null))
            {
                IMagic playerCap = player.getCapability(CapabilityLoader.magic, null);
                IStorage<IMagic> storage = CapabilityLoader.magic.getStorage();
                
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
        IThirst thirst = event.original.getCapability(CapabilityLoader.thirst, null);
        if(thirst.getDrinkStats() != null)
        {
            NBTTagCompound nbtThirst = (NBTTagCompound) CapabilityLoader.thirst.getStorage()
                    .writeNBT(CapabilityLoader.thirst, thirst, null);
            if(event.wasDeath)
            {
                thirst.getDrinkStats().setDrinkLevel(20);
                thirst.getDrinkStats().setDrinkSaturationLevel(5.0F);
            }
            CapabilityLoader.thirst.getStorage().readNBT(CapabilityLoader.thirst,
                    event.entityPlayer.getCapability(CapabilityLoader.thirst, null), null, nbtThirst);
        }

        IMagic magic = event.original.getCapability(CapabilityLoader.magic, null);
        NBTTagCompound nbtPlayer = (NBTTagCompound) CapabilityLoader.magic.getStorage()
                .writeNBT(CapabilityLoader.magic, magic, null);
        if(event.wasDeath)
        {
            float mana = magic.getMaxMana();
            nbtPlayer.setFloat("ManaF", mana);
            nbtPlayer.setShort("Mana", (short) Math.ceil(mana));
        }
        CapabilityLoader.magic.getStorage().readNBT(CapabilityLoader.magic,
                event.entityPlayer.getCapability(CapabilityLoader.magic, null), null, nbtPlayer);
    }
}
