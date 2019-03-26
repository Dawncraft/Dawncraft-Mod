package io.github.dawncraft.client.event;

import net.minecraftforge.common.MinecraftForge;

/**
 * Register some client events.
 *
 * @author QingChenW
 */
public class ClientEventLoader
{
    public static void initClientEvents()
    {
        register(new PlayerRenderEventHandler());
        register(new InputEventHandler());
        register(new TooltipEventHandler());
        register(new GuiEventHandler());
        register(new GuiIngameDawn());
    }

    static void register(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
