package com.github.wdawning.dawncraft.block;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.github.wdawning.dawncraft.tileentity.TileEntityWire;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWire extends BlockContainer
{
	protected BlockWire() {
		super(Material.iron);
        this.setUnlocalizedName("wire");
        this.setHardness(2.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(CreativeTabsLoader.tabEnergy);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityWire();
	}

}
