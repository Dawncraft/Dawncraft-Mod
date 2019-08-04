package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * The computer monitor
 *
 * @author QingChenW
 */
public class BlockComputerMonitor extends BlockMachine
{
    protected static final AxisAlignedBB AABB_X = new AxisAlignedBB(0.45F, 0.0F, 0.0625F, 0.55F, 0.95F, 0.9375F);
    protected static final AxisAlignedBB AABB_Z = new AxisAlignedBB(0.0625F, 0.0F, 0.45F, 0.9375F, 0.95F, 0.55F);
    public EnumMonitorType type;

    public BlockComputerMonitor(EnumMonitorType monitorType)
    {
        super();
        this.type = monitorType;
        this.setCreativeTab(CreativeTabsLoader.COMPUTER);
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
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);
        if (facing.getAxis() == Axis.X)
            return AABB_X;
        else if (facing.getAxis() == Axis.Z)
            return AABB_Z;
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntity tileentity = world.getTileEntity(pos);

        }
        return true;
    }

    @Override
    public void attachItemStackNBT(ItemStack dropStack, World world, EntityPlayer player, BlockPos pos,
            IBlockState state, TileEntity tileentity, ItemStack stack)
    {

    }

    public enum EnumMonitorType
    {
        SIMPLE, ADVANCED, PROFESSIONAL, CUSTOM;
    }
}
