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
    public static void initClientEvents()
    {
        register(new GuiHandler());
        register(new PlayerRenderHandler());
        register(new GuiIngameDawn());
        register(new TooltipEventHandler());
        register(new InputHandler());
    }

    static void register(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
