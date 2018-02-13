package io.github.dawncraft.fluid;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.LogLoader;
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
    public static final ResourceLocation PETROLEUM_STILL = new ResourceLocation(Dawncraft.MODID + ":" + "fluid/petroleum_still");
    public static final ResourceLocation PETROLEUM_FLOWING = new ResourceLocation(Dawncraft.MODID + ":" + "fluid/petroleum_flow");
    
    public static Fluid fluidPetroleum = new Fluid("petroleum", PETROLEUM_STILL, PETROLEUM_FLOWING).setUnlocalizedName("fluidPetroleum").setDensity(8000).setViscosity(850);
    
    public FluidLoader(FMLPreInitializationEvent event)
    {
        fluidPetroleum = register(fluidPetroleum);
    }
    
    public static Fluid register(Fluid fluid)
    {
        if (FluidRegistry.isFluidRegistered(fluidPetroleum))
        {
            String name = fluidPetroleum.getName();
            LogLoader.logger().info("Found fluid {}, the registration is canceled. ", name);
            fluid = FluidRegistry.getFluid(name);
        }
        else
        {
            FluidRegistry.registerFluid(fluidPetroleum);
        }
        return fluid;
    }
}
