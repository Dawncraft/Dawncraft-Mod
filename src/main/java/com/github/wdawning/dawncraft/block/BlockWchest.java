package com.github.wdawning.dawncraft.block;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockWchest extends BlockChest //BlockContainer
{
    public BlockWchest()
    {
        super(0);
        this.setUnlocalizedName("superChest");
        this.setCreativeTab(CreativeTabsLoader.tabFurniture);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
        return new TileEntityChest();
	}
}