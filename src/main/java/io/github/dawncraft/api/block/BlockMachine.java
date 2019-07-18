package io.github.dawncraft.api.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The basic machine block, you can make your own machine by extending it.
 *
 * @author QingChenW
 */
public abstract class BlockMachine extends BlockContainer
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool WORKING = PropertyBool.create("working");

    public BlockMachine()
    {
        this(Material.IRON.getMaterialMapColor());
    }

    public BlockMachine(MapColor color)
    {
        super(Material.IRON, color);
        this.setHardness(5.0F);
        this.setResistance(10.0f);
        this.setHarvestLevel("hammer", 1);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, WORKING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(WORKING, false);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = world.getTileEntity(pos);
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(world, pos, state);
    }

    private void setDefaultFacing(World world, BlockPos pos, IBlockState state)
    {
        if (!world.isRemote)
        {
            Block north = world.getBlockState(pos.north()).getBlock();
            Block south = world.getBlockState(pos.south()).getBlock();
            Block west = world.getBlockState(pos.west()).getBlock();
            Block east = world.getBlockState(pos.east()).getBlock();
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && north.isFullBlock(state) && !south.isFullBlock(state))
            {
                facing = EnumFacing.SOUTH;
            }
            else if (facing == EnumFacing.SOUTH && south.isFullBlock(state) && !north.isFullBlock(state))
            {
                facing = EnumFacing.NORTH;
            }
            else if (facing == EnumFacing.WEST && west.isFullBlock(state) && !east.isFullBlock(state))
            {
                facing = EnumFacing.EAST;
            }
            else if (facing == EnumFacing.EAST && east.isFullBlock(state) && !west.isFullBlock(state))
            {
                facing = EnumFacing.WEST;
            }

            world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        world.updateComparatorOutputLevel(pos, this);
        super.breakBlock(world, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
    }

    public static void setBlockState(boolean active, World world, BlockPos pos)
    {
        world.setBlockState(pos, world.getBlockState(pos).withProperty(WORKING, active), 3);
    }
}
