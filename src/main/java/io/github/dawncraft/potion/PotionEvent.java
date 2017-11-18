package io.github.dawncraft.potion;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEvent
{
    private Random rand = new Random();

    public PotionEvent(FMLInitializationEvent event) {}

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && player.isPotionActive(PotionLoader.potionParalysis))
        {
            if(this.rand.nextBoolean())
            {
                event.setCanceled(true);
                player.addChatMessage(new ChatComponentTranslation("chat.potion.paralysis"));
            }
        }
    }
}
