package io.github.dawncraft.client.event;

import io.github.dawncraft.config.KeyLoader;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerRenderEventHandler
{
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event)
    {
	if (KeyLoader.use.isKeyDown())
	    event.setNewfov(10.0F);
    }
}
