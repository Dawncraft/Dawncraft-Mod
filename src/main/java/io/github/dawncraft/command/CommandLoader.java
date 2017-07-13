package io.github.dawncraft.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Register commands.
 * 
 * @author QingChenW
 */
public class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandMagic());
    }
}
