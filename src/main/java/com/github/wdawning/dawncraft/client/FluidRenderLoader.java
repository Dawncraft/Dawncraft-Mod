package com.github.wdawning.dawncraft.client;

import com.github.wdawning.dawncraft.fluid.FluidLoader;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class FluidRenderLoader
{
    public FluidRenderLoader(FMLPreInitializationEvent event)
    {
        FluidLoader.registerRenders();
        event.getModLog().info("The registy of fluid render is finished. ");
    }
}