package com.github.wdawning.dawncraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class BlockComputerCase extends Block
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool POWER = PropertyBool.create("power");
	
	public BlockComputerCase(Material computerType)
	{
		super(computerType);
		this.setHardness(3.0f);
		this.setResistance(3.0f);
		this.setStepSound(soundTypePiston);
		this.setCreativeTab(CreativeTabsLoader.tabComputer);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(POWER, Boolean.FALSE));
	}

    //Simple computer case
    public static class SimpleComputer extends BlockComputerCase
    {
    	public SimpleComputer()
    	{
    		super(BlockLoader.MACHINE);
    		this.setUnlocalizedName("simpleComputer");
    		this.setHarvestLevel("ItemPickaxe", 1);
            this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                    .withProperty(POWER, Boolean.FALSE));
    	}
    }

    //High computer case
    public static class HighComputer extends BlockComputerCase
    {
    	public HighComputer()
    	{
    		super(BlockLoader.MACHINE);
    		this.setUnlocalizedName("highComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
            this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                    .withProperty(POWER, Boolean.FALSE));
    	}
    }

    //Pro computer case 
    public static class ProComputer extends BlockComputerCase
    {
    	public ProComputer()
    	{
    		super(BlockLoader.MACHINE);
    		this.setUnlocalizedName("proComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
            this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                    .withProperty(POWER, Boolean.FALSE));
    	}
    }

    //Super computer case
    public static class SuperComputer extends BlockComputerCase
    {
    	public SuperComputer()
    	{
    		super(BlockLoader.MACHINE);
    		this.setUnlocalizedName("superComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
            this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                    .withProperty(POWER, Boolean.FALSE));
    	}
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
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
		if(worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.NORTH || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.SOUTH)
		{
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
		}
		else if(worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.WEST || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.EAST)
		{
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
		}
    }
    
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
        IBlockState origin = super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
        int facing = ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
        int power = ((Boolean) state.getValue(POWER)).booleanValue() ? 4 : 0;
        return facing | power;
	}
	
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        Boolean power = Boolean.valueOf((meta & 4) != 0);
        return this.getDefaultState().withProperty(FACING, facing).withProperty(POWER, power);
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING, POWER);
    }
	
    @Override
    public int getRenderType()
    {
        return 3;
    }
}