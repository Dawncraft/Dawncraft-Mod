package io.github.dawncraft.fluid;

import io.github.dawncraft.dawncraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register fluid in the class.
 * 
 * @author QingChenW
 */
public class FluidLoader
{
    public static final ResourceLocation PETROLEUM_STILL = new ResourceLocation(dawncraft.MODID + ":" + "fluid/petroleum_still");
    public static final ResourceLocation PETROLEUM_FLOWING = new ResourceLocation(dawncraft.MODID + ":" + "fluid/petroleum_flow");
    
    public static Fluid fluidPetroleum = new Fluid("petroleum", PETROLEUM_STILL, PETROLEUM_FLOWING).setUnlocalizedName("fluidPetroleum").setDensity(8000).setViscosity(850);
    
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
