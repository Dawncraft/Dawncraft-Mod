package com.github.wdawning.dawncraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.github.wdawning.dawncraft.common.CommonProxy;

/**
 * Dawncraft Mod
 * 
 * @author QingChenW
 * @version mc-1.8
 **/

@Mod(modid = dawncraft.MODID, name = dawncraft.NAME, version = dawncraft.VERSION, guiFactory = dawncraft.GUI_FACTORY, acceptedMinecraftVersions = "[1.8,)")

public class dawncraft
{
    public static final String MODID = "dawncraft";
    public static final String NAME = "Dawncraft Mod";
    public static final String VERSION = "1.8";
    public static final String GUI_FACTORY = "com.github.wdawning.dawncraft.client.gui.GuiFactory";
    
    @Instance(dawncraft.MODID)
    public static dawncraft instance;
    
    @SidedProxy(clientSide = "com.github.wdawning.dawncraft.client.ClientProxy", serverSide = "com.github.wdawning.dawncraft.common.CommonProxy")
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
