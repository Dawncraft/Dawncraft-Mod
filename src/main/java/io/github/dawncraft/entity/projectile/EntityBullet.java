package io.github.dawncraft.entity.projectile;

import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * EntityBullet 子弹实体类
 *
 * @author QingChenW
 */
public class EntityBullet extends EntityThrowable
{
    private double damage = 1.0D;
    private int knockbackStrength = 1;

    public EntityBullet(World world)
    {
        super(world);
        this.setSize(0.2F, 0.2F);
    }

    public EntityBullet(World world, double x, double y, double z)
    {
        super(world);
        this.setSize(0.2F, 0.2F);
    }

    public EntityBullet(World world, EntityLivingBase shooter)
    {
        super(world);
        this.setSize(0.2F, 0.2F);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public float getEyeHeight()
    {
        return 0.0F;
    }

    public void setDamage(double damage)
    {
        this.damage = damage;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public void setKnockbackStrength(int knockbackStrength)
    {
        this.knockbackStrength = knockbackStrength;
    }

    public int getKnockbackStrength()
    {
        return this.knockbackStrength;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        if (tagCompund.hasKey("damage", 99))
        {
            this.damage = tagCompund.getDouble("damage");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setDouble("damage", this.damage);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null && result.entityHit instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) result.entityHit;

            if (player.capabilities.disableDamage || this.getThrower() instanceof EntityPlayer && !((EntityPlayer) this.getThrower()).canAttackPlayer(player))
            {
                result.entityHit = null;
            }
        }

        if (result.entityHit != null)
        {
            Entity entity = result.entityHit;
            int damage = (int) this.damage;

            DamageSource damagesource = DamageSourceLoader.causeBulletDamage(this, this.getThrower() == null ? this : this.getThrower());

            if (entity.attackEntityFrom(damagesource, damage))
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

                    if (this.knockbackStrength > 0)
                    {
                        float value = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (value > 0.0F)
                        {
                            entity.addVelocity(this.motionX * this.knockbackStrength * 0.02D / value, 0.01D, this.motionZ * this.knockbackStrength * 0.02D / value);
                        }
                    }

                    if (this.getThrower() instanceof EntityLivingBase)
                    {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.getThrower());
                        EnchantmentHelper.applyArthropodEnchantments(this.getThrower(), entitylivingbase);
                    }
                }

                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                if (!(entity instanceof EntityEnderman))
                {
                    this.setDead();
                }
            }
            else
            {
                this.motionX *= -0.10D;
                this.motionY *= -0.10D;
                this.motionZ *= -0.10D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
            }
        }
        /*
        else if (result.typeOfHit == Type.BLOCK)
        {
            BlockPos pos = result.getBlockPos();
            IBlockState blockState = this.world.getBlockState(pos);
            this.setPosition(pos.getX(), pos.getY(), pos.getZ());
            this.motionX = (float)(result.hitVec.x - this.posX);
            this.motionY = (float)(result.hitVec.y - this.posY);
            this.motionZ = (float)(result.hitVec.z - this.posZ);
            float f5 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / f5 * 0.05D;
            this.posY -= this.motionY / f5 * 0.05D;
            this.posZ -= this.motionZ / f5 * 0.05D;
            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;

            if (blockState.getMaterial() != Material.AIR)
            {
                this.inTile.onEntityCollision(this.world, pos, blockState, this);

                // wc新加,子弹打到玻璃上
                if(!this.world.isRemote && (this.inTile == Blocks.GLASS || this.inTile == Blocks.GLASS_PANE))
                {
                    this.world.sendBlockBreakProgress(this.getEntityId(), pos, 4);// 强度随伤害改变
                    this.setDead();
                }
            }
        }
         */
    }
}
