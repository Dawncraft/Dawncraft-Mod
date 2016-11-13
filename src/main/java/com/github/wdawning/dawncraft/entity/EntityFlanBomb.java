package com.github.wdawning.dawncraft.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFlanBomb extends EntityThrowable
{
    public EntityFlanBomb(World worldIn)
    {
        super(worldIn);
    }

    public EntityFlanBomb(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityFlanBomb(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    { 
        if (movingObjectPosition.entityHit != null)
        {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()).setDifficultyScaled().setExplosion(), 8.0F);
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, true);
        }
        
        this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 4.0F, true);
        
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}