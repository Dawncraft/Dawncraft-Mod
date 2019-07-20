package io.github.dawncraft.block;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFurnitureAlarmClock extends BlockHorizontal
{
    public BlockFurnitureAlarmClock()
    {
        super(Material.ANVIL);
        this.setHarvestLevel("hammer", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new ExtendedBlockState(this, new IProperty<?>[]{ FACING }, new IUnlistedProperty<?>[]{ OBJModel.OBJProperty.INSTANCE });
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        return facing;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        IExtendedBlockState oldState = (IExtendedBlockState) state;
        TRSRTransformation transform = new TRSRTransformation(state.getValue(FACING));
        OBJModel.OBJState objState = new OBJModel.OBJState(Lists.newArrayList(OBJModel.Group.ALL), true, transform);
        return oldState.withProperty(OBJModel.OBJProperty.INSTANCE, objState);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
}
