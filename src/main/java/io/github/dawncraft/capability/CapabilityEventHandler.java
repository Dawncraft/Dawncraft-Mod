package io.github.dawncraft.capability;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.AttributesLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

/**
 * Some events that are used to attach capabilities.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class CapabilityEventHandler
{
    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getObject();
            event.addCapability(IPlayerThirst.domain, new CapabilityThirst.Provider(player));

            player.getAttributeMap().registerAttribute(AttributesLoader.MAX_MANA);
            event.addCapability(IPlayerMagic.domain, new CapabilityMagic.Provider(player));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerLoggedInEvent event)
    {
        IPlayerMagic playerMagic = event.player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        playerMagic.getCooldownTracker().sendAll();
        playerMagic.getSkillInventoryContainer().onLearnGuiOpened(playerMagic);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerRespawnEvent event)
    {
        IPlayerMagic playerMagic = event.player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        playerMagic.getSkillInventoryContainer().onLearnGuiOpened(playerMagic);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        IPlayerThirst oldThirst = event.getOriginal().getCapability(CapabilityLoader.PLAYER_THIRST, null);
        IPlayerThirst newThirst = event.getEntityPlayer().getCapability(CapabilityLoader.PLAYER_THIRST, null);
        newThirst.cloneCapability(oldThirst, event.isWasDeath());

        IPlayerMagic oldMagic = event.getOriginal().getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        IPlayerMagic newMagic = event.getEntityPlayer().getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        newMagic.cloneCapability(oldMagic, event.isWasDeath());
    }
}
