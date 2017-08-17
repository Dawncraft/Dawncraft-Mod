package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRocket extends EntityThrowable
{
    private float strength = 4.0F;

    public EntityRocket(World worldIn)
    {
        super(worldIn);
        this.renderDistanceWeight = 8.0D;
        this.setSize(0.8F, 0.8F);
    }
    
    public EntityRocket(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.renderDistanceWeight = 8.0D;
        this.setSize(0.8F, 0.8F);
        this.setPosition(x, y, z);
    }

    public EntityRocket(World worldIn, EntityLivingBase shooterIn, float velocity, float inaccuracy)
    {
        super(worldIn, shooterIn);
        this.renderDistanceWeight = 8.0D;
        this.setSize(0.8F, 0.8F);
        /*
        this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 2.0F, inaccuracy + 1.0F);
         */
    }

    public EntityRocket(World worldIn, EntityLivingBase shooterIn, EntityLivingBase targetIn, float velocity, float inaccuracy)
    {
        super(worldIn, shooterIn);
        this.setSize(0.8F, 0.8F);
        this.renderDistanceWeight = 10.0D;

        //this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {
        super.setThrowableHeading(x, y, z, velocity, inaccuracy);
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }
    
    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (movingObjectPosition.entityHit != null)
        {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()).setDifficultyScaled().setExplosion(), 8.0F);
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, true);
        }

        this.worldObj.createExplosion(this, this.posX, this.posY + this.height / 2.0F, this.posZ, 4.0F, true);

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
    }
}