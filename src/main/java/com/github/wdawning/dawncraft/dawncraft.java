package com.github.wdawning.dawncraft;

import com.github.wdawning.dawncraft.common.CommonProxy;

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
   @version mcmod.version
**/

@Mod(modid = dawncraft.MODID, name = dawncraft.NAME, version = dawncraft.VERSION, acceptedMinecraftVersions = "[1.8,)")

public class dawncraft
{
    public static final String MODID = "dawncraft";
    public static final String NAME = "Dawn Craft Mod";
    public static final String VERSION = "1.8";

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
        new LuaTest();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.serverStarting(event);
    }
}