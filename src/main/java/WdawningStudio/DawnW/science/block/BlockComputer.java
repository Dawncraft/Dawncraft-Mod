package WdawningStudio.DawnW.science.block;

import java.util.Random;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockComputer extends Block
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockComputer(Material computerType)
	{
		super(computerType);
	}

	//Simple computer case
    public static class SimpleComputer extends BlockComputer
    {
    	public SimpleComputer()
    	{
    		super(Material.glass);
    		this.setUnlocalizedName("simpleComputer");
    		this.setHarvestLevel("ItemPickaxe", 1);
    		this.setHardness(3.0f);
        	this.setResistance(3.0f);
        	this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        	this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }

    //High computer case
    public static class HighComputer extends BlockComputer
    {
    	public HighComputer()
    	{
    		super(Material.glass);
    		this.setUnlocalizedName("highComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
    		this.setHardness(3.0f);
    		this.setResistance(3.0f);
    		this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }

    //Pro computer case 
    public static class ProComputer extends BlockComputer
    {
    	public ProComputer()
    	{
    		super(Material.glass);
    		this.setUnlocalizedName("proComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
    		this.setHardness(3.0f);
    		this.setResistance(3.0f);
    		this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }

    //Super computer case
    public static class SuperComputer extends BlockComputer
    {
    	public SuperComputer()
    	{
    		super(Material.glass);
    		this.setUnlocalizedName("superComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
    		this.setHardness(3.0f);
    		this.setResistance(3.0f);
    		this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }
    
/*
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            Block block = worldIn.getBlockState(pos.north()).getBlock();
            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }*/
}