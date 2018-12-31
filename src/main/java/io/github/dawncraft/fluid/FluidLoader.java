package io.github.dawncraft.fluid;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.LogLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Register some fluids.
 *
 * @author QingChenW
 */
public class FluidLoader
{
    public static final ResourceLocation PETROLEUM_STILL = new ResourceLocation(Dawncraft.MODID + ":" + "fluid/petroleum_still");
    public static final ResourceLocation PETROLEUM_FLOWING = new ResourceLocation(Dawncraft.MODID + ":" + "fluid/petroleum_flow");
    
    public static Fluid fluidPetroleum = new Fluid("petroleum", PETROLEUM_STILL, PETROLEUM_FLOWING).setUnlocalizedName("fluidPetroleum").setDensity(8000).setViscosity(850);
    
    public static void initFluids()
    {
        register(fluidPetroleum);
    }
    
    /**
     * Register a fluid. If it has been registered by any other mod, then replace it.
     *
     * @param fluid The fluid to be registered.
     */
    public static void register(Fluid fluid)
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
    }
}
