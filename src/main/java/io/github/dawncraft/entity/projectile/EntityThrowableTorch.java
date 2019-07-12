package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityThrowableTorch extends EntityThrowable
{
    public int rotateTimer = 0;
    
    public EntityThrowableTorch(World world)
    {
        super(world);
    }
    
    public EntityThrowableTorch(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
    }

    public EntityThrowableTorch(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }
    
    @Override
    protected float getVelocity()
    {
        return 0.65F;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.rotateTimer = this.rotateTimer % 360 + 1;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (movingObjectPosition.entityHit != null)
        {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.onFire, 4.0F);
        }

        if (movingObjectPosition.typeOfHit == MovingObjectType.BLOCK)
        {
            this.makeFlames(movingObjectPosition.getBlockPos().offset(movingObjectPosition.sideHit));
            
            if (!this.worldObj.isRemote)
            {
                this.setDead();
            }
        }
    }
    
    public void makeFlames(BlockPos blockPos)
    {
        int r = 3;
        for (int dx = -r; dx <= r; ++dx)
        {
            for (int dz = -r; dz <= r; ++dz)
            {
                if (dx * dx + dz * dz <= r * r)
                {
                    if (this.rand.nextInt(3) == 0)
                    {
                        int x = blockPos.getX() + dx;
                        int y = blockPos.getY();
                        int z = blockPos.getZ() + dz;
                        BlockPos blockPos2 = new BlockPos(x, y, z);
                        if (!this.worldObj.getBlockState(blockPos2).getBlock().getMaterial().isReplaceable())
                        {
                            if (this.worldObj.getBlockState(blockPos2.up()).getBlock().getMaterial().isReplaceable())
                            {
                                this.worldObj.setBlockState(blockPos2.up(), Blocks.fire.getDefaultState());
                                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX(), y, blockPos.getZ(), dx, 0, dz, new int[0]);
                            }
                        }
                        else
                        {
                            if (this.worldObj.getBlockState(blockPos2.down()).getBlock().getMaterial().isReplaceable())
                                blockPos2 = blockPos2.down();
                            if (this.worldObj.getBlockState(blockPos2.down()).getBlock().isFullBlock())
                            {
                                this.worldObj.setBlockState(blockPos2, Blocks.fire.getDefaultState());
                                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX(), y, blockPos.getZ(), dx, 0, dz, new int[0]);
                            }
                        }
                    }
                }
            }
        }
    }
}
