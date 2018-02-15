package io.github.dawncraft.capability;

import io.github.dawncraft.network.MessageWindowSkills;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.network.MessagePlayerSpelling;
import io.github.dawncraft.network.MessageUpdateMana;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityEvent
{
    public CapabilityEvent(FMLInitializationEvent event) {}

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent.Entity event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            player.getAttributeMap().registerAttribute(AttributesLoader.maxMana);
            event.addCapability(CapabilityLoader.res_magic, new CapabilityMagic.Provider(player));
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.entity;
            if (player.hasCapability(CapabilityLoader.magic, null))
            {
                IMagic magic = player.getCapability(CapabilityLoader.magic, null);
                IStorage<IMagic> storage = CapabilityLoader.magic.getStorage();
                
                ISkillInventory inventory = magic.getInventory();
                List<SkillStack> list = new ArrayList<SkillStack>();
                for(int i = 0; i < inventory.getSizeInventory(); i++)
                    list.add(inventory.getStackInSlot(i));
                NetworkLoader.instance.sendTo(new MessageWindowSkills(0, list), player);
                NetworkLoader.instance.sendTo(new MessageUpdateMana(magic.getMana()), player);
                NetworkLoader.instance.sendTo(new MessagePlayerSpelling(magic.getSpellAction(), magic.getSkillInSpellCount(), magic.getPublicCooldownCount()), player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        Capability<IMagic> capability = CapabilityLoader.magic;
        IStorage<IMagic> storage = capability.getStorage();

        if (event.original.hasCapability(capability, null) && event.entityPlayer.hasCapability(capability, null))
        {
            IMagic magic = event.original.getCapability(capability, null);
            NBTTagCompound nbt = (NBTTagCompound) storage.writeNBT(capability, magic, null);
            if(event.wasDeath)
            {
                float mana = magic.getMaxMana();
                nbt.setFloat("ManaF", mana);
                nbt.setShort("Mana", (short) Math.ceil(mana));
            }
            storage.readNBT(capability, event.entityPlayer.getCapability(capability, null), null, nbt);
        }
    }
}
