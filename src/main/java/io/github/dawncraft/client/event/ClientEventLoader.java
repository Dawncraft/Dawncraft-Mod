package io.github.dawncraft.client.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register some client events.
 *
 * @author QingChenW
 */
public class ClientEventLoader
{
    public ClientEventLoader(FMLInitializationEvent event)
    {
        register(new GuiEventHandler(event));
        register(new PlayerRenderEventHandler(event));
        register(new GuiIngameDawn(event));
        register(new TooltipEventHandler(event));
        register(new InputHandler(event));
    }

    static void register(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
