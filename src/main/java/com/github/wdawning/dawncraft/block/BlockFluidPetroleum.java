package com.github.wdawning.dawncraft.block;

import net.minecraft.block.material.Material;

import net.minecraftforge.fluids.BlockFluidClassic;

import com.github.wdawning.dawncraft.fluid.FluidLoader;

public class BlockFluidPetroleum extends BlockFluidClassic
{
	public BlockFluidPetroleum()
    {
        super(FluidLoader.fluidPetroleum, Material.water);
        this.setUnlocalizedName("fluidPetroleum");
    }
}