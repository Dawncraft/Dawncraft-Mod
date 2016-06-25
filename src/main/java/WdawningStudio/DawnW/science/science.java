package WdawningStudio.DawnW.science;

import WdawningStudio.DawnW.science.common.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
   @author QingChenW
   @version is mcmod.version
**/

@Mod(modid = science.MODID, name = science.NAME, version = science.VERSION, acceptedMinecraftVersions = "[1.8,)")

public class science
{
    public static final String MODID = "wcscience";
    public static final String NAME = "Wc's Sience Mod";
    public static final String VERSION = "For mc1.8";

    @Instance(science.MODID)
    public static science instance;
    
    @SidedProxy(clientSide = "WdawningStudio.DawnW.science.client.ClientProxy", serverSide = "WdawningStudio.DawnW.science.common.CommonProxy")
    public static CommonProxy proxy;
    
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
        proxy.postInit(event);
    }
}