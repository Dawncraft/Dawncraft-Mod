package io.github.dawncraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author QingChenW
 *
 */
public class BlockDawnPortal extends BlockBreakable
{
    public BlockDawnPortal()
    {
        super(Material.portal, false, MapColor.obsidianColor);
        this.setHardness(-1F);
        this.setLightLevel(0.75F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN ? super.shouldSideBeRendered(worldIn, pos, side) : false;
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {}
    
    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null)
        {
            if(entityIn instanceof EntityPlayerMP)
            {
                EntityPlayerMP playerMP = (EntityPlayerMP) entityIn;
                if (playerMP.dimension != 23)
                {
                    //                    playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, 23, new WorldTeleporterDawn(playerMP.mcServer.worldServerForDimension(23)));
                    
                    BlockPos blockpos = entityIn.worldObj.getTopSolidOrLiquidBlock(new BlockPos(8, 97, 8));
                    //                    playerMP.moveToBlockPosAndAngles(blockpos, playerMP.rotationYaw, playerMP.rotationPitch);
                    playerMP.playerNetServerHandler.setPlayerLocation(blockpos.getX(), blockpos.getY(), blockpos.getZ(), playerMP.rotationYaw, playerMP.rotationPitch);
                    MinecraftServer.getServer().worldServerForDimension(23).setSpawnPoint(blockpos);

                    //                    playerMP.triggerAchievement(AchievementLoader.dawnArrival);
                }
                else
                {
                    //                    playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, 0, new WorldTeleporterDawn(playerMP.mcServer.worldServerForDimension(0)));

                    BlockPos blockpos = entityIn.worldObj.getTopSolidOrLiquidBlock(playerMP.mcServer.worldServerForDimension(0).getSpawnPoint());
                    //                    playerMP.moveToBlockPosAndAngles(blockpos, playerMP.rotationYaw, playerMP.rotationPitch);
                    playerMP.playerNetServerHandler.setPlayerLocation(blockpos.getX(), blockpos.getY(), blockpos.getZ(), playerMP.rotationYaw, playerMP.rotationPitch);
                }
            }
            else
            {
                this.sendEntityToDimension(entityIn, 23);
            }
        }
    }

    public void sendEntityToDimension(Entity entityIn, int dimensionId)
    {
        if (!entityIn.worldObj.isRemote && !entityIn.isDead)
        {
            entityIn.worldObj.theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int dim = entityIn.dimension;
            WorldServer worldserver = minecraftserver.worldServerForDimension(dim);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
            entityIn.dimension = dimensionId;

            if (dim == 23 && dimensionId == 23)
            {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                entityIn.dimension = 0;
            }

            entityIn.worldObj.removeEntity(entityIn);
            entityIn.isDead = false;
            entityIn.worldObj.theProfiler.startSection("reposition");
            //            minecraftserver.getConfigurationManager().transferEntityToWorld(entityIn, dim, worldserver, worldserver1,  new WorldTeleporterDawn(worldserver1));
            entityIn.worldObj.theProfiler.endStartSection("reloading");
            Entity transferEntity = EntityList.createEntityByName(EntityList.getEntityString(entityIn), worldserver1);
            
            if (transferEntity != null)
            {
                transferEntity.copyDataFromOld(entityIn);

                if (dim == 23 && dimensionId == 23)
                {
                    BlockPos blockpos = entityIn.worldObj.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
                    transferEntity.moveToBlockPosAndAngles(blockpos, transferEntity.rotationYaw, transferEntity.rotationPitch);
                }
                else
                {
                    BlockPos blockpos = entityIn.worldObj.getTopSolidOrLiquidBlock(new BlockPos(8, 97, 8));
                    transferEntity.moveToBlockPosAndAngles(blockpos, transferEntity.rotationYaw, transferEntity.rotationPitch);
                }

                worldserver1.spawnEntityInWorld(transferEntity);
            }
            
            entityIn.isDead = true;
            entityIn.worldObj.theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            entityIn.worldObj.theProfiler.endSection();
        }
    }

    @Override
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

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return null;
    }
}
