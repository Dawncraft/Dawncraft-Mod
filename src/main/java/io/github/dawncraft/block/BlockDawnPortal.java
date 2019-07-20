package io.github.dawncraft.block;

import java.util.Random;

import io.github.dawncraft.world.TeleporterDawn;
import io.github.dawncraft.world.WorldInit;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A portal which can make you access to the dawn world.
 *
 * @author QingChenW
 */
public class BlockDawnPortal extends BlockBreakable
{
    protected static final AxisAlignedBB END_PORTAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public BlockDawnPortal()
    {
        super(Material.PORTAL, false, MapColor.BLACK);
        this.setBlockUnbreakable();
        this.setLightLevel(1.0F);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return END_PORTAL_AABB;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (!world.isRemote && !entity.isRiding() && !entity.isBeingRidden() && entity.isNonBoss() && entity.getEntityBoundingBox().intersects(state.getBoundingBox(world, pos).offset(pos)))
        {
            entity.changeDimension(WorldInit.DAWNWORLD.getId(), new TeleporterDawn((WorldServer) world));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return facing == EnumFacing.DOWN ? super.shouldSideBeRendered(state, world, pos, facing) : false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        double x = pos.getX() + rand.nextFloat();
        double y = pos.getY() + 0.8F;
        double z = pos.getZ() + rand.nextFloat();
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
