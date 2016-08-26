package com.github.wdawning.dawncraft.block;

import java.util.List;
import java.util.Random;

import com.github.wdawning.dawncraft.worldgen.WorldTeleporterDawn;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDawnPortal extends BlockBreakable
{
	public BlockDawnPortal()
	{
		super(Material.portal, false);
		this.setUnlocalizedName("dawnPortal");
        this.setLightLevel(1.0F);
	}
	
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN ? super.shouldSideBeRendered(worldIn, pos, side) : false;
    }
    
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {}

    public boolean isFullCube()
    {
        return false;
    }
    
    public int quantityDropped(Random random)
    {
        return 0;
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null && !worldIn.isRemote)
        {
        	if(entityIn.dimension != 23)
        	{
            	if(entityIn instanceof EntityPlayerMP)
            	{
                    worldIn.theProfiler.startSection("changeDimension");
                    ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
                    WorldTeleporterDawn teleporter = new WorldTeleporterDawn(MinecraftServer.getServer().worldServerForDimension(23));
                    scm.transferPlayerToDimension((EntityPlayerMP) entityIn, 23, teleporter);
                    worldIn.theProfiler.endSection();
                }
            	else
            	{
                    worldIn.theProfiler.startSection("changeDimension");
                    ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
                    WorldTeleporterDawn teleporter = new WorldTeleporterDawn(MinecraftServer.getServer().worldServerForDimension(23));
                    scm.transferEntityToWorld(entityIn, 23,(WorldServer) worldIn,MinecraftServer.getServer().worldServerForDimension(23),teleporter);
                    worldIn.theProfiler.endSection();
            	}
        	}
        	else
        	{
            	if(entityIn instanceof EntityPlayerMP)
            	{
                    worldIn.theProfiler.startSection("changeDimension");
                    ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
                    WorldTeleporterDawn teleporter = new WorldTeleporterDawn(MinecraftServer.getServer().worldServerForDimension(0));
                    WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(0);
                    scm.transferPlayerToDimension((EntityPlayerMP) entityIn, 0,teleporter);
                    BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(worldserver.getSpawnPoint());
                    entityIn.moveToBlockPosAndAngles(blockpos, entityIn.rotationYaw, entityIn.rotationPitch);
                    worldIn.theProfiler.endSection();
                }
            	else
            	{
                    worldIn.theProfiler.startSection("changeDimension");
                    ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
                    WorldTeleporterDawn teleporter = new WorldTeleporterDawn(MinecraftServer.getServer().worldServerForDimension(0));
                    WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(0);
                    scm.transferEntityToWorld(entityIn, 0,(WorldServer) worldIn,worldserver,teleporter);
                    BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(worldserver.getSpawnPoint());
                    entityIn.moveToBlockPosAndAngles(blockpos, entityIn.rotationYaw, entityIn.rotationPitch);
                    worldIn.theProfiler.endSection();
            	}
        	}
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        double d0 = (double)((float)pos.getX() + rand.nextFloat());
        double d1 = (double)((float)pos.getY() + 0.8F);
        double d2 = (double)((float)pos.getZ() + rand.nextFloat());
        double d3 = 0.0D;
        double d4 = 0.0D;
        double d5 = 0.0D;
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return null;
    }
    
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.obsidianColor;
    }
}
