package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityRocket extends EntityThrowable
{
    private float strength = 4.0F;

    public EntityRocket(World world)
    {
        super(world);
        Entity.setRenderDistanceWeight(10.0D);
        this.setSize(0.8F, 0.8F);
    }

    public EntityRocket(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        Entity.setRenderDistanceWeight(10.0D);
        this.setSize(0.8F, 0.8F);
        this.setPosition(x, y, z);
    }

    public EntityRocket(World world, EntityLivingBase shooter, float velocity, float inaccuracy)
    {
        super(world, shooter);
        Entity.setRenderDistanceWeight(10.0D);
        this.setSize(0.8F, 0.8F);
    }

    public EntityRocket(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity, float inaccuracy)
    {
        super(world, shooter);
        this.setSize(0.8F, 0.8F);
        Entity.setRenderDistanceWeight(10.0D);

        //this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        super.shoot(x, y, z, velocity, inaccuracy);
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
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()).setDifficultyScaled().setExplosion(), 8.0F);
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, true);
        }

        this.world.createExplosion(this, this.posX, this.posY + this.height / 2.0F, this.posZ, 4.0F, true);

        if (!this.world.isRemote)
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