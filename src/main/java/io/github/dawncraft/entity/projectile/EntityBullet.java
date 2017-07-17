package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/** EntityBullet
 * 
 * @author QingChenW
 */
public class EntityBullet extends Entity implements IProjectile
{
    public Entity shooter;
    private int ticksAlive;
    private int ticksInAir;
    private double damage;
    
	public EntityBullet(World worldIn)
	{
		super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.renderDistanceWeight = 10.0D;
	}
	
    public EntityBullet(World worldIn, double x, double y, double z)
    {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.renderDistanceWeight = 10.0D;
        this.setPosition(x, y, z);
    }
    
    public EntityBullet(World worldIn, EntityLivingBase shooterIn, float velocity, float inaccuracy)
    {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.renderDistanceWeight = 10.0D;
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
    
    public EntityBullet(World worldIn, EntityLivingBase shooterIn, EntityLivingBase targetIn, float velocity, float inaccuracy)
    {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.renderDistanceWeight = 10.0D;
        
        this.shooter = shooterIn;
        this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + (double) shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
        // TODO 子弹对实体攻击
    }
    
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
        float f = MathHelper.sqrt_double(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * 180.0D / Math.PI);
	}
    
    @Override
    protected void entityInit() {}
	
	@Override
    public void onUpdate()
    {
		
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
