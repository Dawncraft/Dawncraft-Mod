package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.fluid.FluidLoader;
import WdawningStudio.DawnW.science.fluid.FluidPetroleum;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidPetroleum extends BlockFluidClassic
{
	public BlockFluidPetroleum()
    {
        super(FluidLoader.fluidPetroleum, Material.water);
        this.setUnlocalizedName("fluidPetroleum");
    }
}