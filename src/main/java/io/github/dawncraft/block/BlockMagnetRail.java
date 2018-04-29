package io.github.dawncraft.block;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMagnetRail extends BlockRailBase
{
    public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection>create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
    {
        @Override
        public boolean apply(BlockRailBase.EnumRailDirection direction)
        {
            return direction != BlockRailBase.EnumRailDirection.NORTH_EAST && direction != BlockRailBase.EnumRailDirection.NORTH_WEST && direction != BlockRailBase.EnumRailDirection.SOUTH_EAST && direction != BlockRailBase.EnumRailDirection.SOUTH_WEST;
        }
    });
    
    public BlockMagnetRail()
    {
        super(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
    }
    
    @Override
    public boolean isFlexibleRail(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public float getRailMaxSpeed(World world, net.minecraft.entity.item.EntityMinecart cart, BlockPos pos)
    {
        return 0.6f;
    }

    @Override
    public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }
    
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, SHAPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(SHAPE).getMetadata();
    }
    
    @Override
    protected void onNeighborChangedInternal(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (neighborBlock.canProvidePower())
        {
            this.func_176564_a(worldIn, pos, state, false);
        }
    }
    
    @Override
    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos)
    {
        if (cart.riddenByEntity != null)
        {
            cart.motionX *= 1.66D;
            cart.motionZ *= 1.66D;
        }
        else
        {
            cart.motionX *= 1.25D;
            cart.motionZ *= 1.25D;
        }
    }
}
