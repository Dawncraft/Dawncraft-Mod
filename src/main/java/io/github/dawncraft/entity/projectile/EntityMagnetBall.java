package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityMagnetBall extends EntityThrowable
{
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
	double d0 = blockPos.getX();
	int i = blockPos.getY();
	double d1 = blockPos.getZ();
	double d2 = d0 - this.posX;
	double d3 = d1 - this.posZ;
	float f = MathHelper.sqrt(d2 * d2 + d3 * d3);

	if (f > 12.0F)
	{
	}
	else
	{
	}
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
	for (int i = 0; i < 8; ++i)
	{
	    this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
	}

	if (!this.world.isRemote)
	{
	    this.setDead();
	}
    }
}