package io.github.dawncraft.entity.projectile;

import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/** EntityBullet 子弹实体类
 * <br>部分参考了Lambda Innovation Team的LIUtils,感谢!</br>
 *
 * @author QingChenW
 */
public class EntityBullet extends Entity implements IProjectile
{
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    private int inData;
    private boolean inGround;
    /** 事实上,我认为这个没用,一般来说子弹是比较稳定的不太会抖动吧 */
    public int bulletShake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 1.0D;
    private int knockbackStrength = 1;

    public EntityBullet(World worldIn)
    {
        super(worldIn);
        Entity.setRenderDistanceWeight(10.0D);
        this.setSize(0.2F, 0.2F);
    }

    public EntityBullet(World worldIn, double x, double y, double z)
    {
        super(worldIn);
        Entity.setRenderDistanceWeight(10.0D);
        this.setSize(0.2F, 0.2F);
        this.setPosition(x, y, z);
    }

    public EntityBullet(World worldIn, EntityLivingBase shooterIn, float velocity)
    {
        super(worldIn);
        Entity.setRenderDistanceWeight(10.0D);
        this.shootingEntity = shooterIn;
        this.setSize(0.2F, 0.2F);
        this.setLocationAndAngles(shooterIn.posX, shooterIn.posY + shooterIn.getEyeHeight(), shooterIn.posZ, shooterIn.rotationYaw, shooterIn.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.posY -= 0.10D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.shoot(this.motionX, this.motionY, this.motionZ, velocity * 2.0F, 1.0F);
    }

    public EntityBullet(World worldIn, EntityLivingBase shooterIn, EntityLivingBase targetIn, float velocity, float inaccuracy)
    {
        super(worldIn);
        Entity.setRenderDistanceWeight(10.0D);
        this.shootingEntity = shooterIn;
        this.setSize(0.2F, 0.2F);

        this.posY = shooterIn.posY + shooterIn.getEyeHeight() - 0.10D;
        double d0 = targetIn.posX - shooterIn.posX;
        double d1 = targetIn.getEntityBoundingBox().minY + targetIn.height / 3.0F - this.posY;
        double d2 = targetIn.posZ - shooterIn.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f1 = (float)-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI);
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(shooterIn.posX + d4, this.posY, shooterIn.posZ + d5, f, f1);
            float f2 = (float)(d3 * 0.20D);
            this.shoot(d0, d1 + f2, d2, velocity, inaccuracy);
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / f;
        y = y / f;
        z = z / f;
        x = x + this.rand.nextGaussian() * 0.0075D * inaccuracy;
        y = y + this.rand.nextGaussian() * 0.0075D * inaccuracy;
        z = z + this.rand.nextGaussian() * 0.0075D * inaccuracy;
        x = x * velocity;
        y = y * velocity;
        z = z * velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        // 子弹角度
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ + this.motionZ);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / Math.PI);
        // 子弹当前所处方块
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        // 判断方块
        if (iblockstate.getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);
            if (axisalignedbb != null && axisalignedbb.contains(new Vec3d(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }
        // 摇晃动画计时器
        if (this.bulletShake > 0)
        {
            --this.bulletShake;
        }
        // 是否在地里
        if (this.inGround)
        {
            // 如果真的卡在方块里了
            if (iblockstate.getBlock() == this.inTile && iblockstate.getBlock().getMetaFromState(iblockstate) == this.inData)
            {
                // 那就卡着呗,到点就没了
                ++this.ticksInGround;

                if (this.ticksInGround >= 200)
                {
                    this.setDead();
                }
            }
            else
            {
                // 赶紧出来,接着掉下去
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            // 让子弹飞
            ++this.ticksInAir;
            Vec3d oldvec3 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d newvec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult movingobjectposition = this.world.rayTraceBlocks(oldvec3, newvec3, false, true, false);
            oldvec3 = new Vec3d(this.posX, this.posY, this.posZ);
            newvec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                newvec3 = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y, movingobjectposition.hitVec.z);
            }

            Entity entity = null;
            double distance = 0.0D;

            for (Entity e : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().contract(this.motionX, this.motionY, this.motionZ).expand(0.5D, 0.5D, 0.5D)))
            {
                if (e.canBeCollidedWith() && (e != this.shootingEntity || this.ticksInAir >= 2))
                {
                    float f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = e.getEntityBoundingBox().expand(f1, f1, f1);
                    RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(oldvec3, newvec3);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = oldvec3.squareDistanceTo(movingobjectposition1.hitVec);

                        if (d1 < distance || distance == 0.0D)
                        {
                            entity = e;
                            distance = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new RayTraceResult(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
                    int damage = (int) this.damage;

                    if (this.getIsCritical())
                    {
                        damage += 1;
                    }

                    DamageSource damagesource = DamageSourceLoader.causeBulletDamage(this, this.shootingEntity == null ? this : this.shootingEntity);

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, damage))
                    {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

                            if (this.knockbackStrength > 0)
                            {
                                float f7 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f7 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.02D / f7, 0.01D, this.motionZ * this.knockbackStrength * 0.02D / f7);
                                }
                            }

                            if (this.shootingEntity instanceof EntityLivingBase)
                            {
                                EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                                EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                            }
                        }

                        //this.playSound(Dawncraft.MODID + ":" + "random.gunhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));// 子弹击中声音

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
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
                        this.ticksInAir = 0;
                    }
                }
                else
                {
                    BlockPos blockpos1 = movingobjectposition.getBlockPos();
                    IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
                    this.xTile = blockpos1.getX();
                    this.yTile = blockpos1.getY();
                    this.zTile = blockpos1.getZ();
                    this.inTile = iblockstate1.getBlock();
                    this.inData = this.inTile.getMetaFromState(iblockstate1);
                    this.motionX = (float)(movingobjectposition.hitVec.x - this.posX);
                    this.motionY = (float)(movingobjectposition.hitVec.y - this.posY);
                    this.motionZ = (float)(movingobjectposition.hitVec.z - this.posZ);
                    float f5 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / f5 * 0.05D;
                    this.posY -= this.motionY / f5 * 0.05D;
                    this.posZ -= this.motionZ / f5 * 0.05D;
                    //this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));子弹飞过声
                    this.inGround = true;
                    this.bulletShake = 2;
                    this.setIsCritical(false);

                    if (iblockstate1.getMaterial() != Material.AIR)
                    {
                        this.inTile.onEntityCollision(this.world, blockpos1, iblockstate1, this);

                        // wc新加,子弹打到玻璃上
                        if(!this.world.isRemote && (this.inTile == Blocks.GLASS || this.inTile == Blocks.GLASS_PANE))
                        {
                            this.world.sendBlockBreakProgress(this.getEntityId(), blockpos1, 4);// 强度随伤害改变
                            this.setDead();
                        }
                    }

                    if (this.ticksInAir > 600)
                    {
                        this.setDead();
                    }
                }
            }
        }
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

    public void setDamage(double damageIn)
    {
        this.damage = damageIn;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public void setKnockbackStrength(int knockbackStrengthIn)
    {
        this.knockbackStrength = knockbackStrengthIn;
    }

    public int getKnockbackStrength()
    {
        return this.knockbackStrength;
    }

    public void setIsCritical(boolean critical)
    {

    }

    public boolean getIsCritical()
    {
        return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        this.xTile = tagCompund.getShort("xTile");
        this.yTile = tagCompund.getShort("yTile");
        this.zTile = tagCompund.getShort("zTile");
        this.ticksInGround = tagCompund.getShort("life");

        if (tagCompund.hasKey("inTile", 8))
        {
            this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
        }
        else
        {
            this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 255);
        }

        this.inData = tagCompund.getByte("inData") & 255;
        this.bulletShake = tagCompund.getByte("shake") & 255;
        this.inGround = tagCompund.getByte("inGround") == 1;

        if (tagCompund.hasKey("damage", 99))
        {
            this.damage = tagCompund.getDouble("damage");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setShort("xTile", (short)this.xTile);
        tagCompound.setShort("yTile", (short)this.yTile);
        tagCompound.setShort("zTile", (short)this.zTile);
        tagCompound.setShort("life", (short)this.ticksInGround);
        ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
        tagCompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        tagCompound.setByte("inData", (byte)this.inData);
        tagCompound.setByte("shake", (byte)this.bulletShake);
        tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        tagCompound.setDouble("damage", this.damage);
    }
}
