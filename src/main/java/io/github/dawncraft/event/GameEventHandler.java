package io.github.dawncraft.event;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.item.ItemInitializer;
import io.github.dawncraft.stats.AchievementLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * Handle some events that related to game logics.
 *
 * @author QingChenW
 */
public class GameEventHandler
{
    public GameEventHandler() {}

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.entity;
            player.triggerAchievement(AchievementLoader.basic);
        }
    }

    @SubscribeEvent
    public void playerTickEvent(PlayerTickEvent event)
    {
        if (event.phase == Phase.END)
        {
            EntityPlayer player = event.player;
            IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
            playerMagic.update();
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntitySavage)
        {
            EntitySavage savage = (EntitySavage) event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() == ItemInitializer.faeces)
            {
                player.attackEntityFrom(DamageSourceLoader.ger, 20.0F);
                player.worldObj.createExplosion(savage, savage.posX, savage.posY, savage.posZ, 4.0F, false);
                savage.setDead();
            }
        }
    }
}
