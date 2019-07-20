package io.github.dawncraft.server;

import io.github.dawncraft.CommonProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * The server proxy of Dawncraft Mod.
 *
 * @author QingChenW
 */
public class ServerProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event)
    {
        super.serverStarting(event);
    }

    @Override
    public void interModComms(IMCEvent event)
    {
        super.interModComms(event);
    }
}
