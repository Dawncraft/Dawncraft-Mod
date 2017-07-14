package io.github.dawncraft;

import io.github.dawncraft.server.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.SidedProxy;

/**
 * Dawncraft Mod For Minecraft with Forge
 * 
 * @version mc-1.8.9
 * @author QingChenW
 **/
@Mod(modid = dawncraft.MODID, name = dawncraft.NAME, version = dawncraft.VERSION, guiFactory = dawncraft.GUI_FACTORY, acceptedMinecraftVersions = "1.8.9")
public class dawncraft
{
    public static final String MODID = "dawncraft";
    public static final String NAME = "Dawncraft Mod";
    public static final String VERSION = "@version@";
    public static final String GUI_FACTORY = "io.github.dawncraft.client.gui.GuiFactory";
    
    @Instance(dawncraft.MODID)
    public static dawncraft instance;
    
    @SidedProxy(clientSide = "io.github.dawncraft.client.ClientProxy", serverSide = "io.github.dawncraft.server.ServerProxy")
    public static ServerProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        new LuaTest();
        proxy.postInit(event);
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.serverStarting(event);
    }
    
    @EventHandler
    public void interModComms(IMCEvent event)
    {
        proxy.interModComms(event);
    }
}
