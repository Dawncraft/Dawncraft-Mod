package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockFurniture;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFurnitureTable extends BlockFurniture
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool EAST = PropertyBool.create("east");

    public BlockFurnitureTable(EnumMaterialType type)
    {
        super(type);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false)
                .withProperty(SOUTH, false).withProperty(WEST, false));
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        if (facing == EnumFacing.UP) return true;
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        boolean north = world.getBlockState(pos.north()).getBlock() == this;
        boolean south = world.getBlockState(pos.south()).getBlock() == this;
        boolean west = world.getBlockState(pos.west()).getBlock() == this;
        boolean east = world.getBlockState(pos.east()).getBlock() == this;
        return state.withProperty(NORTH, north).withProperty(EAST, east).withProperty(SOUTH, south).withProperty(WEST, west);
    }
}
