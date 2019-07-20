package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Electric cable
 *
 * @author QingChenW
 */
public class BlockElectricCable extends Block
{
    public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> UP = PropertyEnum.create("up", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> DOWN = PropertyEnum.create("down", EnumAttachPosition.class);
    protected static final AxisAlignedBB CABLE_AABB = new AxisAlignedBB(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);

    public BlockElectricCable()
    {
        super(Material.CIRCUITS);
        this.setHardness(2.0F);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, EnumAttachPosition.NONE)
                .withProperty(EAST, EnumAttachPosition.NONE)
                .withProperty(SOUTH, EnumAttachPosition.NONE)
                .withProperty(WEST, EnumAttachPosition.NONE)
                .withProperty(UP, EnumAttachPosition.NONE)
                .withProperty(DOWN, EnumAttachPosition.NONE));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        float x1 = 0.375F;
        float x2 = 0.625F;
        float z1 = x1;
        float z2 = x2;
        float y1 = x1;
        float y2 = x2;

        if (this.getAttachPosition(world, pos, EnumFacing.WEST) != EnumAttachPosition.NONE)
            x1 = 0.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.EAST) != EnumAttachPosition.NONE)
            x2 = 1.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.NORTH) != EnumAttachPosition.NONE)
            z1 = 0.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.SOUTH) != EnumAttachPosition.NONE)
            z2 = 1.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.DOWN) != EnumAttachPosition.NONE)
            y1 = 0.0F;
        if (this.getAttachPosition(world, pos, EnumFacing.UP) != EnumAttachPosition.NONE)
            y2 = 1.0F;

        return new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }

    public EnumAttachPosition getAttachPosition(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();

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
