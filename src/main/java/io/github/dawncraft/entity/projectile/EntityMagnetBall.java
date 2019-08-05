package io.github.dawncraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }
}