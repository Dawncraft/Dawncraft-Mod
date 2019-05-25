package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMagnetBall extends EntityThrowable
{
    /** 'x' location the ball should fly towards. */
    private double targetX;
    /** 'y' location the ball should fly towards. */
    private double targetY;
    /** 'z' location the ball should fly towards. */
    private double targetZ;
    private int despawnTimer;

    public EntityMagnetBall(World world)
    {
        super(world);
    }

    public EntityMagnetBall(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
    }

    public EntityMagnetBall(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public void moveTowards(BlockPos blockPos)
    {
        double d0 = (double) blockPos.getX();
        int i = blockPos.getY();
        double d1 = (double) blockPos.getZ();
        double d2 = d0 - this.posX;
        double d3 = d1 - this.posZ;
        float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);
        
        if (f > 12.0F)
        {
            this.targetX = this.posX + d2 / (double) f * 12.0D;
            this.targetZ = this.posZ + d3 / (double) f * 12.0D;
            this.targetY = this.posY + 8.0D;
        }
        else
        {
            this.targetX = d0;
            this.targetY = (double) i;
            this.targetZ = d1;
        }
        
        this.despawnTimer = 0;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}