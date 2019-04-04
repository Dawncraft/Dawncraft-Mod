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
        registerEvent(new PlayerRenderEventHandler());
        registerEvent(new InputEventHandler());
        registerEvent(new TooltipEventHandler());
        registerEvent(new GuiEventHandler());
        registerEvent(new GuiIngameDawn());
    }
    
    private static void registerEvent(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
