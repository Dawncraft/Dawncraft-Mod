package io.github.dawncraft.api.block;

import java.util.Random;

import javax.annotation.Nullable;

import io.github.dawncraft.api.tileentity.TileEntityMachine;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.tileentity.TileEntityHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
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
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
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
        this.setCreativeTab(CreativeTabsLoader.MACHINE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, false));
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
    public IBlockState withRotation(IBlockState state, Rotation rotation)
    {
        return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror)
    {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(WORKING, false);
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
            IBlockState north = world.getBlockState(pos.north());
            IBlockState south = world.getBlockState(pos.south());
            IBlockState west = world.getBlockState(pos.west());
            IBlockState east = world.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock())
            {
                facing = EnumFacing.SOUTH;
            }
            else if (facing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock())
            {
                facing = EnumFacing.NORTH;
            }
            else if (facing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock())
            {
                facing = EnumFacing.EAST;
            }
            else if (facing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock())
            {
                facing = EnumFacing.WEST;
            }

            world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityMachine)
            {
                ((TileEntityMachine) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public abstract boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return TileEntityHelper.calcRedstone(world.getTileEntity(pos));
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileentity, ItemStack stack)
    {
        if (tileentity != null)
        {
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);

            if (!world.isRemote)
            {
                int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                Item item = this.getItemDropped(state, world.rand, i);

                if (item != Items.AIR)
                {
                    ItemStack dropStack = new ItemStack(item, this.quantityDropped(world.rand));
                    if (tileentity instanceof IWorldNameable && ((IWorldNameable) tileentity).hasCustomName())
                        dropStack.setStackDisplayName(((IWorldNameable) tileentity).getName());
                    this.attachItemStackNBT(dropStack, world, player, pos, state, tileentity, stack);
                    spawnAsEntity(world, pos, dropStack);
                }
            }
        }
        else
        {
            super.harvestBlock(world, player, pos, state, null, stack);
        }
    }

    public void attachItemStackNBT(ItemStack dropStack, World world, EntityPlayer player, BlockPos pos,
            IBlockState state, TileEntity tileentity, ItemStack stack) {}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntityMachine)
        {
            TileEntityHelper.dropInventoriesItems(world, pos, TileEntityHelper.getInventoriesFromTileEntity(tileentity));
            world.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
    }

    public static void setBlockState(boolean active, World world, BlockPos pos)
    {
        if (!(world.getBlockState(pos).getBlock() instanceof BlockMachine)) return; // FIXME 有时会设置空气方块的状态

        world.setBlockState(pos, world.getBlockState(pos).withProperty(WORKING, active), 3);

        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }
}
