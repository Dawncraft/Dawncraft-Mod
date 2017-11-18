package io.github.dawncraft;

import io.github.dawncraft.server.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Dawncraft Mod For Minecraft with Forge Mod Loader.
 *
 * @version mc-1.8.9
 * @author QingChenW
 **/
@Mod(modid = Dawncraft.MODID, name = Dawncraft.NAME, version = Dawncraft.VERSION, guiFactory = Dawncraft.GUI_FACTORY, acceptedMinecraftVersions = "1.8.9")
public class Dawncraft
{
    /** The id of Dawncraft Mod. */
    public static final String MODID = "Dawncraft";
    /** The name of Dawncraft Mod. */
    public static final String NAME = "Dawncraft Mod";
    /** The version of Dawncraft Mod. It will replaced by Gradle.*/
    public static final String VERSION = "@version@";
    /** The gui factory of Dawncraft Mod. */
    public static final String GUI_FACTORY = "io.github.dawncraft.client.gui.GuiFactory";
    /** The instance of Dawncraft Mod. */
    @Instance(Dawncraft.MODID)
    public static Dawncraft instance;
    /** The instance of Server Proxy. */
    @SidedProxy(clientSide = "io.github.dawncraft.client.ClientProxy", serverSide = "io.github.dawncraft.server.ServerProxy")
    public static ServerProxy proxy;
    
    /** {@link FMLPreInitializationEvent} */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }
    
    /** {@link FMLInitializationEvent} */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    /** {@link FMLPostInitializationEvent} */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        new ScriptTest();
        proxy.postInit(event);
    }
    
    /** {@link FMLServerStartingEvent} */
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.serverStarting(event);
    }
    
    /** {@link IMCEvent} */
    @EventHandler
    public void interModComms(IMCEvent event)
    {
        proxy.interModComms(event);
    }
}
