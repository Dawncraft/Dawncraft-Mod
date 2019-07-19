package io.github.dawncraft.potion;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEventHandler
{
    private Random rand = new Random();

    public PotionEventHandler() {}

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event)
    {
	EntityPlayer player = event.getEntityPlayer();
	if (player.isServerWorld() && player.isPotionActive(PotionLoader.potionParalysis))
	{
	    if(this.rand.nextBoolean())
	    {
		event.setCanceled(true);
		player.sendMessage(new TextComponentTranslation("chat.potion.paralysis"));
	    }
	}
    }
}
