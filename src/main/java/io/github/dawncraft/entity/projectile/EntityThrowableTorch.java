package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.ON_FIRE, 4.0F);
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            this.makeFlames(result.getBlockPos().offset(result.sideHit));

            if (!this.world.isRemote)
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
                        if (!this.world.getBlockState(blockPos2).getMaterial().isReplaceable())
                        {
                            if (this.world.getBlockState(blockPos2.up()).getMaterial().isReplaceable())
                            {
                                this.world.setBlockState(blockPos2.up(), Blocks.FIRE.getDefaultState());
                                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX(), y, blockPos.getZ(), dx, 0, dz);
                            }
                        }
                        else
                        {
                            if (this.world.getBlockState(blockPos2.down()).getMaterial().isReplaceable())
                                blockPos2 = blockPos2.down();
                            if (this.world.getBlockState(blockPos2.down()).isFullBlock())
                            {
                                this.world.setBlockState(blockPos2, Blocks.FIRE.getDefaultState());
                                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX(), y, blockPos.getZ(), dx, 0, dz);
                            }
                        }
                    }
                }
            }
        }
    }
}
