package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * The computer case.
 *
 * @author QingChenW
 */
public class BlockComputerCase extends BlockMachine
{
    protected static final AxisAlignedBB AABB_X = new AxisAlignedBB(0.00D, 0.00D, 0.25D, 1.00D, 1.00D, 0.75D);
    protected static final AxisAlignedBB AABB_Z = new AxisAlignedBB(0.25D, 0.00D, 0.00D, 0.75D, 1.00D, 1.00D);
    public EnumCaseType type;

    public BlockComputerCase(EnumCaseType caseType)
    {
        super();
        this.type = caseType;
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
    public TileEntity createNewTileEntity(World world, int meta)
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
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
        if (true)
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Owner", player.getName());
            dropStack.setTagCompound(nbt);
        }
    }

    public enum EnumCaseType
    {
        SIMPLE, ADVANCED, PROFESSIONAL, CUSTOM;
    }
}
