package io.github.dawncraft.capability;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.network.MessageMana;
import io.github.dawncraft.network.NetworkLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
            ResourceLocation res = new ResourceLocation(Dawncraft.MODID + ":" + "magic");
            ICapabilitySerializable<NBTTagCompound> provider = new CapabilityMana.Provider();
            event.addCapability(res, provider);
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.entity;
            if (player.hasCapability(CapabilityLoader.mana, null))
            {
                IMana mana = player.getCapability(CapabilityLoader.mana, null);
                IStorage<IMana> storage = CapabilityLoader.mana.getStorage();
                
                MessageMana message = new MessageMana();
                message.nbt = (NBTTagCompound) storage.writeNBT(CapabilityLoader.mana, mana, null);
                
                NetworkLoader.instance.sendTo(message, player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        Capability<IMana> capability = CapabilityLoader.mana;
        IStorage<IMana> storage = capability.getStorage();

        if (event.original.hasCapability(capability, null) && event.entityPlayer.hasCapability(capability, null))
        {
            NBTBase nbt = storage.writeNBT(capability, event.original.getCapability(capability, null), null);
            storage.readNBT(capability, event.entityPlayer.getCapability(capability, null), null, nbt);
        }
    }
}
