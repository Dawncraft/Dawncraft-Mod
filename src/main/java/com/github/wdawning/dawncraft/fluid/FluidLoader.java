package com.github.wdawning.dawncraft.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class FluidLoader
{
    public static Fluid fluidPetroleum = new FluidPetroleum();
    
    public FluidLoader(FMLPreInitializationEvent event)
    {
        if (FluidRegistry.isFluidRegistered(fluidPetroleum))
        {
            event.getModLog().info("Found fluid {}, the registration is canceled. ", fluidPetroleum.getName());
            fluidPetroleum = FluidRegistry.getFluid(fluidPetroleum.getName());
        }
        else
        {
            FluidRegistry.registerFluid(fluidPetroleum);
        }
    }
}
