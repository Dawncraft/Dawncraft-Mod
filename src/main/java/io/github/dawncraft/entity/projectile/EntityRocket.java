package io.github.dawncraft.entity.projectile;

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
        this.setSize(0.8F, 0.8F);
    }

    public EntityRocket(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        this.setSize(0.8F, 0.8F);
    }

    public EntityRocket(World world, EntityLivingBase shooter)
    {
        super(world, shooter);
        this.setSize(0.8F, 0.8F);
    }

    public void setStrength(float strength)
    {
        this.strength = strength;
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.01F;
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()).setDifficultyScaled().setExplosion(), 8.0F);
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, this.strength, true);
        }

        this.world.createExplosion(this, this.posX, this.posY + this.height / 2.0F, this.posZ, this.strength, true);

        if (!this.world.isRemote)
        {
            this.setDead();
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.strength = tagCompund.getFloat("strength");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setFloat("strength", this.strength);
    }
}