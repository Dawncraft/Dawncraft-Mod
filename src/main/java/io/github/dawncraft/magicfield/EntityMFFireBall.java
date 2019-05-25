package io.github.dawncraft.magicfield;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
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
        double norm = MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / norm * acceleration;
        this.accelerationY = accelY / norm * acceleration;
        this.accelerationZ = accelZ / norm * acceleration;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObject)
    {
        if (!this.getEntityWorld().isRemote)
        {
            if (movingObject.entityHit != null)
            {
                boolean flag = movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), this.damage);
                
                if (flag)
                {
                    this.applyEnchantments(this.shootingEntity, movingObject.entityHit);
                    
                    if (!movingObject.entityHit.isImmuneToFire())
                    {
                        movingObject.entityHit.setFire(5);
                    }
                }
            }
            else
            {
                BlockPos blockPos = movingObject.getBlockPos();
                this.getEntityWorld().playSoundEffect(blockPos.getX(), blockPos.getY(), blockPos.getZ(), "fire.ignite", 1.0F, 1.0F);
            }
            this.setDead();
        }
    }
}
