package io.github.dawncraft.block;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMagnetRail extends BlockRailBase
{
    public static final PropertyEnum<EnumRailDirection> SHAPE = PropertyEnum.<EnumRailDirection>create("shape", EnumRailDirection.class, new Predicate<EnumRailDirection>()
    {
        @Override
        public boolean apply(EnumRailDirection direction)
        {
            return direction != EnumRailDirection.NORTH_EAST && direction != EnumRailDirection.NORTH_WEST && direction != EnumRailDirection.SOUTH_EAST && direction != EnumRailDirection.SOUTH_WEST;
        }
    });

    public BlockMagnetRail()
    {
        super(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH));
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
    {
        return 0.6f;
    }

    @Override
    public IProperty<EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SHAPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, EnumRailDirection.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(SHAPE).getMetadata();
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos)
    {
        if (!cart.isBeingRidden())
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
