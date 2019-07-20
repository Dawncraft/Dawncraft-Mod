package io.github.dawncraft.fluid;

import io.github.dawncraft.Dawncraft;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class FluidEventHandler
{
    @SubscribeEvent
    public static void onFillBucket(FillBucketEvent event)
    {

    }
}
