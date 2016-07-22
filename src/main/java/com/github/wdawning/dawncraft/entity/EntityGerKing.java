package com.github.wdawning.dawncraft.entity;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.item.ItemLoader;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGerKing extends EntityMob implements IBossDisplayData
{
    public EntityGerKing(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
   
    @Override
    protected void dropFewItems(boolean arg1, int arg2)
    {
        this.dropItem(ItemLoader.gerHeart, 1);
    }
    
    protected String getLivingSound()
    {
        return dawncraft.MODID + ":" + "mob.gerking.say";
    }

    protected String getHurtSound()
    {
        return dawncraft.MODID + ":" + "mob.gerking.hurt";
    }

    protected String getDeathSound()
    {
        return dawncraft.MODID + ":" + "mob.gerking.death";
    }
}
