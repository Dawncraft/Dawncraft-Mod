package io.github.dawncraft.fluid;

import io.github.dawncraft.Dawncraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Register some fluids.
 *
 * @author QingChenW
 */
public class FluidInit
{
    public static final ResourceLocation PETROLEUM_STILL = new ResourceLocation(Dawncraft.MODID + ":" + "fluid/petroleum_still");
    public static final ResourceLocation PETROLEUM_FLOWING = new ResourceLocation(Dawncraft.MODID + ":" + "fluid/petroleum_flow");
    public static final ResourceLocation PETROLEUM_OVERLAY = new ResourceLocation("blocks/water_overlay");

    public static final Fluid PETROLEUM = new Fluid("petroleum", PETROLEUM_STILL, PETROLEUM_FLOWING, PETROLEUM_OVERLAY, 0xFFFFFFFF).setUnlocalizedName("fluidPetroleum").setDensity(850).setViscosity(1750);

    public static void initFluids()
    {
        registerFluid(PETROLEUM);
    }

    /**
     * Register a fluid.
     *
     * @param fluid The fluid to register.
     */
    private static void registerFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}
