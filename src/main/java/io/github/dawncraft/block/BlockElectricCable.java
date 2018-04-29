package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;

/**
 * Electric cable
 *
 * @author QingChenW
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
        this.setBlockBounds(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, BlockElectricCable.EnumAttachPosition.NONE)
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
    protected BlockState createBlockState()
    {
        return new BlockState(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
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
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(WEST, this.getAttachPosition(world, pos, EnumFacing.WEST))
                .withProperty(EAST, this.getAttachPosition(world, pos, EnumFacing.EAST))
                .withProperty(NORTH, this.getAttachPosition(world, pos, EnumFacing.NORTH))
                .withProperty(SOUTH, this.getAttachPosition(world, pos, EnumFacing.SOUTH))
                .withProperty(UP, this.getAttachPosition(world, pos, EnumFacing.UP))
                .withProperty(DOWN, this.getAttachPosition(world, pos, EnumFacing.DOWN));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
    {
        float x1 = 0.375F;
        float x2 = 0.625F;
        float z1 = x1;
        float z2 = x2;
        float y1 = x1;
        float y2 = x2;
        
        if (this.getAttachPosition(world, pos, EnumFacing.WEST) != BlockElectricCable.EnumAttachPosition.NONE)
            x1 = 0.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.EAST) != BlockElectricCable.EnumAttachPosition.NONE)
            x2 = 1.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.NORTH) != BlockElectricCable.EnumAttachPosition.NONE)
            z1 = 0.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.SOUTH) != BlockElectricCable.EnumAttachPosition.NONE)
            z2 = 1.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.DOWN) != BlockElectricCable.EnumAttachPosition.NONE)
            y1 = 0.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.UP) != BlockElectricCable.EnumAttachPosition.NONE)
            y2 = 1.0F;
        
        this.setBlockBounds(x1, y1, z1, x2, y2, z2);
    }
    
    public EnumAttachPosition getAttachPosition(IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        Block block = world.getBlockState(pos.offset(side)).getBlock();

        if (block == this)
            return BlockElectricCable.EnumAttachPosition.CABLE;
        else if (block instanceof BlockMachine)
            return BlockElectricCable.EnumAttachPosition.MACHINE;
        else
            return BlockElectricCable.EnumAttachPosition.NONE;
    }
    
    static enum EnumAttachPosition implements IStringSerializable
    {
        NONE("none"), CABLE("cable"), MACHINE("machine");
        
        private final String name;
        
        private EnumAttachPosition(String name)
        {
            this.name = name;
        }
        
        @Override
        public String getName()
        {
            return this.name;
        }
        
        @Override
        public String toString()
        {
            return this.getName();
        }
    }
}
