package com.github.wdawning.dawncraft.block;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
//import com.github.wdawning.dawncraft.tileentity.TileEntityWchest;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockWchest extends BlockContainer
{
    public BlockWchest()
    {
        super(Material.wood);
        this.setUnlocalizedName("superChest");
        this.setCreativeTab(CreativeTabsLoader.tabFurniture);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
        return new TileEntityChest();
	}
}