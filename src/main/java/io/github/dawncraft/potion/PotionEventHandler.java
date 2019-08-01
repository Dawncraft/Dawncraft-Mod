package io.github.dawncraft.potion;

import io.github.dawncraft.Dawncraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class PotionEventHandler
{
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player.isServerWorld() && player.isPotionActive(PotionInit.PARALYSIS))
        {
            if (player.world.rand.nextBoolean())
            {
                event.setCanceled(true);
                player.sendMessage(new TextComponentTranslation("chat.potion.paralysis"));
            }
        }
    }
}
