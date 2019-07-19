package io.github.dawncraft.magicfield;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityMFFireBall extends EntityFireball
{
    private float damage;

    public EntityMFFireBall(World world)
    {
	super(world);
	this.setSize(0.35F, 0.35F);
    }

    public EntityMFFireBall(World world, EntityLivingBase shooter, float damage, double acceleration)
    {
	this(world);
	this.shootingEntity = shooter;
	this.damage = damage;
	this.setLocationAndAngles(shooter.posX, shooter.posY + 1, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
	this.motionX = this.motionY = this.motionZ = 0.0D;
	double accelX = -MathHelper.sin(shooter.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(shooter.rotationPitch / 180.0F * (float) Math.PI);
	double accelY = -MathHelper.sin(shooter.rotationPitch / 180.0F * (float) Math.PI);
	double accelZ = MathHelper.cos(shooter.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(shooter.rotationPitch / 180.0F * (float) Math.PI);
	double norm = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
	this.accelerationX = accelX / norm * acceleration;
	this.accelerationY = accelY / norm * acceleration;
	this.accelerationZ = accelZ / norm * acceleration;
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
	if (!this.getEntityWorld().isRemote)
	{
	    if (result.entityHit != null)
	    {
		boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), this.damage);

		if (flag)
		{
		    this.applyEnchantments(this.shootingEntity, result.entityHit);

		    if (!result.entityHit.isImmuneToFire())
		    {
			result.entityHit.setFire(5);
		    }
		}
	    }
	    else
	    {
		this.getEntityWorld().playSound(null, result.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    }
	    this.setDead();
	}
    }
}
