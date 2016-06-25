package WdawningStudio.DawnW.science.client;

import WdawningStudio.DawnW.science.fluid.FluidLoader;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class FluidRenderLoader
{
    public FluidRenderLoader(FMLPreInitializationEvent event)
    {
        FluidLoader.registerRenders();
        event.getModLog().info("The registy of fluid render is finished. ");
    }
}