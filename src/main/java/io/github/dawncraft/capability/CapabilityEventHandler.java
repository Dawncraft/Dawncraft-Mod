package io.github.dawncraft.capability;

import io.github.dawncraft.entity.AttributesLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

/**
 * Some events that are used to attach capabilities.
 *
 * @author QingChenW
 */
public class CapabilityEventHandler
{
    public CapabilityEventHandler() {}

    @SubscribeEvent
    public void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
	if (event.getObject() instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) event.getObject();
	    event.addCapability(IPlayerThirst.domain, new CapabilityThirst.Provider(player));

	    player.getAttributeMap().registerAttribute(AttributesLoader.maxMana);
	    event.addCapability(IPlayerMagic.domain, new CapabilityMagic.Provider(player));
	}
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event)
    {
	IPlayerMagic playerMagic = event.player.getCapability(CapabilityLoader.playerMagic, null);
	playerMagic.getCooldownTracker().sendAll();
	playerMagic.getSkillInventoryContainer().onLearnGuiOpened(playerMagic);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
	IPlayerMagic playerMagic = event.player.getCapability(CapabilityLoader.playerMagic, null);
	playerMagic.getSkillInventoryContainer().onLearnGuiOpened(playerMagic);
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
	IPlayerThirst oldThirst = event.getOriginal().getCapability(CapabilityLoader.playerThirst, null);
	IPlayerThirst newThirst = event.getEntityPlayer().getCapability(CapabilityLoader.playerThirst, null);
	newThirst.cloneCapability(oldThirst, event.isWasDeath());

	IPlayerMagic oldMagic = event.getOriginal().getCapability(CapabilityLoader.playerMagic, null);
	IPlayerMagic newMagic = event.getEntityPlayer().getCapability(CapabilityLoader.playerMagic, null);
	newMagic.cloneCapability(oldMagic, event.isWasDeath());
    }
}
