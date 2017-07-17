package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRocket extends Entity implements IProjectile
{
    public Entity shooter;
    private int ticksAlive;
    private int ticksInAir;
    private float strength = 4.0F;
    
    public EntityRocket(World worldIn)
    {
        super(worldIn);
        this.renderDistanceWeight = 8.0D;
    }

    public EntityRocket(World worldIn, double x, double y, double z)
    {
        super(worldIn);
        this.renderDistanceWeight = 8.0D;
        this.setPosition(x, y, z);
    }
    
    public EntityRocket(World worldIn, EntityLivingBase shooterIn, float velocity, float inaccuracy)
    {
        super(worldIn);
        this.renderDistanceWeight = 8.0D;
        this.shooter = shooterIn;
        this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + (double) shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 2.0F, inaccuracy + 1.0F);
    }
    
    public EntityRocket(World worldIn, EntityLivingBase shooterIn, EntityLivingBase targetIn, float velocity, float inaccuracy)
    {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.renderDistanceWeight = 10.0D;
        
        this.shooter = shooterIn;
        this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + (double) shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
        // TODO 导弹对实体攻击
    }
    
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
		
	}

	@Override
	protected void entityInit()
	{
		
	}
	
	@Override
    public void onUpdate()
    {
		
    }

    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (movingObjectPosition.entityHit != null)
        {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.shooter).setDifficultyScaled().setExplosion(), 8.0F);
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, true);
        }
        
        this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 4.0F, true);
        
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		
	}
}