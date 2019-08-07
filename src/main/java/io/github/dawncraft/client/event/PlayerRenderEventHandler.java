package io.github.dawncraft.client.event;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.KeyLoader;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Dawncraft.MODID)
public class PlayerRenderEventHandler
{
    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event)
    {
        if (KeyLoader.USE.isKeyDown())
            event.setNewfov(20.0F);
    }
}
