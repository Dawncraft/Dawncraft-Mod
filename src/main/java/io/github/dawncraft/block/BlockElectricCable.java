package io.github.dawncraft.block;

import io.github.dawncraft.block.base.BlockMachineBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;

/**
 * @author QingChenW
 *
 */
public class BlockElectricCable extends Block
{
    public static final PropertyEnum NORTH = PropertyEnum.create("north", BlockElectricCable.EnumAttachPosition.class);
    public static final PropertyEnum EAST = PropertyEnum.create("east", BlockElectricCable.EnumAttachPosition.class);
    public static final PropertyEnum SOUTH = PropertyEnum.create("south", BlockElectricCable.EnumAttachPosition.class);
    public static final PropertyEnum WEST = PropertyEnum.create("west", BlockElectricCable.EnumAttachPosition.class);
    public static final PropertyEnum UP = PropertyEnum.create("up", BlockElectricCable.EnumAttachPosition.class);
    public static final PropertyEnum DOWN = PropertyEnum.create("down", BlockElectricCable.EnumAttachPosition.class);
    
    public BlockElectricCable()
    {
        super(Material.circuits);
        this.setHardness(2.0F);
        this.setBlockBounds(0.375F, 0.375F, 0.375F, 0.75F, 0.75F, 0.75F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockElectricCable.EnumAttachPosition.NONE)
                .withProperty(EAST, BlockElectricCable.EnumAttachPosition.NONE)
                .withProperty(SOUTH, BlockElectricCable.EnumAttachPosition.NONE)
                .withProperty(WEST, BlockElectricCable.EnumAttachPosition.NONE)
                .withProperty(UP, BlockElectricCable.EnumAttachPosition.NONE)
                .withProperty(DOWN, BlockElectricCable.EnumAttachPosition.NONE));
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
        float x1 = 6F / 16F;
        float x2 = 1.0F - x1;
        float y1 = x1;
        float y2 = 1.0F - y1;
        float z1 = x1;
        float z2 = 1.0F - z1;
        
        if (getAttachPosition(worldIn, pos, EnumFacing.WEST) != BlockElectricCable.EnumAttachPosition.NONE)
        {
            x1 = 0.0F;
        }
        if (getAttachPosition(worldIn, pos, EnumFacing.EAST) != BlockElectricCable.EnumAttachPosition.NONE)
        {
            x2 = 1.0F;
        }
        if (getAttachPosition(worldIn, pos, EnumFacing.NORTH) != BlockElectricCable.EnumAttachPosition.NONE)
        {
            z1 = 0.0F;
        }
        if (getAttachPosition(worldIn, pos, EnumFacing.SOUTH) != BlockElectricCable.EnumAttachPosition.NONE)
        {
            z2 = 1.0F;
        }
        if (getAttachPosition(worldIn, pos, EnumFacing.DOWN) != BlockElectricCable.EnumAttachPosition.NONE)
        {
            y1 = 0.0F;
        }
        if (getAttachPosition(worldIn, pos, EnumFacing.UP) != BlockElectricCable.EnumAttachPosition.NONE)
        {
            y2 = 1.0F;
        }
        
        this.setBlockBounds(x1, y1, z1, x2, y2, z2);
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
    
    private BlockElectricCable.EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos,
            EnumFacing direction)
    {
        BlockPos blockpos1 = pos.offset(direction);
        Block block = worldIn.getBlockState(pos.offset(direction)).getBlock();
        int flag = canConnect(worldIn, blockpos1, direction);
        
        if (flag == 1)
        {
            return BlockElectricCable.EnumAttachPosition.CABLE;
        }
        else if (flag == 2)
        {
            return BlockElectricCable.EnumAttachPosition.MACHINE;
        }
        else
        {
            return BlockElectricCable.EnumAttachPosition.NONE;
        }
    }
    
    protected static int canConnect(IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        IBlockState state = world.getBlockState(pos);
        
        if (state.getBlock() == BlockLoader.electricCable)
        {
            return 1;
        }
        else if (state.getBlock() instanceof BlockMachineBase)
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
        return this.getDefaultState();
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
    
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, UP, DOWN});
    }
    
    static enum EnumAttachPosition implements IStringSerializable
    {
        NONE("none"), CABLE("cable"), MACHINE("machine");
        
        private final String _name;
        
        private EnumAttachPosition(String name)
        {
            this._name = name;
        }
        
        public String getName()
        {
            return this._name;
        }
        
        public String toString()
        {
            return this.getName();
        }
    }
}
