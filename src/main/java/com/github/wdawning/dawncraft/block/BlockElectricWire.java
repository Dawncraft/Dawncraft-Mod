package com.github.wdawning.dawncraft.block;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElectricWire extends Block
{
    public static final PropertyEnum NORTH = PropertyEnum.create("north", BlockElectricWire.EnumAttachPosition.class);
    public static final PropertyEnum EAST = PropertyEnum.create("east", BlockElectricWire.EnumAttachPosition.class);
    public static final PropertyEnum SOUTH = PropertyEnum.create("south", BlockElectricWire.EnumAttachPosition.class);
    public static final PropertyEnum WEST = PropertyEnum.create("west", BlockElectricWire.EnumAttachPosition.class);
    public static final PropertyEnum UP = PropertyEnum.create("up", BlockElectricWire.EnumAttachPosition.class);
    public static final PropertyEnum DOWN = PropertyEnum.create("down", BlockElectricWire.EnumAttachPosition.class);
    public static final PropertyBool POWER = PropertyBool.create("hasElectricity");
    private boolean canProvidePower = true;
    
    public BlockElectricWire()
	{
		super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockElectricWire.EnumAttachPosition.NONE).withProperty(EAST, BlockElectricWire.EnumAttachPosition.NONE).withProperty(SOUTH, BlockElectricWire.EnumAttachPosition.NONE).withProperty(WEST, BlockElectricWire.EnumAttachPosition.NONE).withProperty(UP, BlockElectricWire.EnumAttachPosition.NONE).withProperty(DOWN, BlockElectricWire.EnumAttachPosition.NONE).withProperty(POWER, Boolean.valueOf(false)));
        this.setUnlocalizedName("wire");
        this.setHardness(2.0F);
        this.setBlockBounds(0.375F, 0.375F, 0.375F, 0.75F, 0.75F, 0.75F);
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
        state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
        state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
        state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
        state = state.withProperty(UP, this.getAttachPosition(worldIn, pos, EnumFacing.UP));
        state = state.withProperty(DOWN, this.getAttachPosition(worldIn, pos, EnumFacing.DOWN));
        return state;
    }
    
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float x1 = 6F / 16F;
        float x2 = 1.0F - x1;
        float y1 = x1;
        float y2 = 1.0F - y1;
        float z1 = x1;
        float z2 = 1.0F - z1;
        
        if(getAttachPosition(worldIn, pos, EnumFacing.WEST) != BlockElectricWire.EnumAttachPosition.NONE)
        {
            x1 = 0.0F;
        }
        if(getAttachPosition(worldIn, pos, EnumFacing.EAST) != BlockElectricWire.EnumAttachPosition.NONE)
        {
            x2 = 1.0F;
        }
        if(getAttachPosition(worldIn, pos, EnumFacing.NORTH) != BlockElectricWire.EnumAttachPosition.NONE)
        {
        	z1 = 0.0F;
        }
        if(getAttachPosition(worldIn, pos, EnumFacing.SOUTH) != BlockElectricWire.EnumAttachPosition.NONE)
        {
        	z2 = 1.0F;
        }
        if(getAttachPosition(worldIn, pos, EnumFacing.DOWN) != BlockElectricWire.EnumAttachPosition.NONE)
        {
        	y1 = 0.0F;
        }
        if(getAttachPosition(worldIn, pos, EnumFacing.UP) != BlockElectricWire.EnumAttachPosition.NONE)
        {
        	y2 = 1.0F;
        }
        
        this.setBlockBounds(x1, y1, z1, x2, y2, z2);
    }
    
    private BlockElectricWire.EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction)
    {
        BlockPos blockpos1 = pos.offset(direction);
        Block block = worldIn.getBlockState(pos.offset(direction)).getBlock();
        int flag = canWireConnect(worldIn, blockpos1, direction);

        if (flag == 1)
        {
            return BlockElectricWire.EnumAttachPosition.ALL;
        }
        else if (flag == 2)
        {
            return BlockElectricWire.EnumAttachPosition.SPECIAL;
        }
        else
        {
            return BlockElectricWire.EnumAttachPosition.NONE;
        }
    }
    
    protected static int canWireConnect(IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() == BlockLoader.pipeWire)
        {
            return 1;
        }
        else if (state.getBlock().getMaterial() == BlockLoader.MACHINE)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }
    
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        Boolean power = Boolean.valueOf((meta & 4) != 0);
        return this.getDefaultState().withProperty(POWER, power);
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        int power = ((Boolean) state.getValue(POWER)).booleanValue() ? 4 : 0;
        return power;
    }

   @Override
   protected BlockState createBlockState()
   {
       return new BlockState(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN, POWER});
   }

    static enum EnumAttachPosition implements IStringSerializable
    {
        NONE("none"),
        ALL("connect"),
        SPECIAL("special");

        private final String name;

        private EnumAttachPosition(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this.name;
        }
    }
}
