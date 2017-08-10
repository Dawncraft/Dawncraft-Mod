package io.github.dawncraft.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author QingChenW
 *
 */
public abstract class BlockMachineBase extends BlockContainer
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool WORKING = PropertyBool.create("working");
    
    public BlockMachineBase()
    {
        this(Material.iron.getMaterialMapColor());
    }
    
    public BlockMachineBase(MapColor color)
    {
        super(Material.iron, color);
        this.setHardness(3.0F);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        IBlockState origin = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        world.updateComparatorOutputLevel(pos, this);
        super.breakBlock(world, pos, state);
    }
    
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING, WORKING);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        Boolean working = Boolean.valueOf((meta & 4) != 0);
        return this.getDefaultState().withProperty(FACING, facing).withProperty(WORKING, working);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int working = state.getValue(WORKING).booleanValue() ? 4 : 0;
        return facing | working;
    }

    @Override
    public int getRenderType()
    {
        return 3;
    }
    
    public static void setBlockState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if (active)
            worldIn.setBlockState(pos, iblockstate.getBlock().getDefaultState()
                    .withProperty(FACING, iblockstate.getValue(FACING)).withProperty(WORKING, Boolean.TRUE), 3);
        else
            worldIn.setBlockState(pos, iblockstate.getBlock().getDefaultState()
                    .withProperty(FACING, iblockstate.getValue(FACING)).withProperty(WORKING, Boolean.FALSE), 3);
        
        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
