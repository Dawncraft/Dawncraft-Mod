package io.github.dawncraft.event;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * Handle some events that related to game logics.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class GameEventHandler
{
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            // player.triggerAchievement(AchievementLoader.basic);
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent event)
    {
        if (event.phase == Phase.END)
        {
            EntityPlayer player = event.player;
            IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
            playerMagic.update();
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(EntityInteract event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player.isServerWorld() && event.getTarget() instanceof EntitySavage)
        {
            EntitySavage savage = (EntitySavage) event.getTarget();
            ItemStack stack = player.getHeldItem(event.getHand());
            if (stack != null && stack.getItem() == ItemInit.FAECES)
            {
                player.attackEntityFrom(DamageSourceLoader.GER, 20.0F);
                player.world.createExplosion(savage, savage.posX, savage.posY, savage.posZ, 4.0F, false);
                savage.setDead();
            }
        }
    }
}
