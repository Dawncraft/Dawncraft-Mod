package com.github.wdawning.dawncraft.block;

import com.github.wdawning.dawncraft.fluid.FluidLoader;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidPetroleum extends BlockFluidClassic
{
	public BlockFluidPetroleum()
    {
        super(FluidLoader.fluidPetroleum, Material.water);
        this.setUnlocalizedName("fluidPetroleum");
    }
}