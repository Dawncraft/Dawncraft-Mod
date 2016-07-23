package com.github.wdawning.dawncraft.entity;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.item.ItemLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGerKing extends EntityMob implements IBossDisplayData
{
    public EntityGerKing(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.experienceValue = 30;
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
    }
    
    public int getTotalArmorValue()
    {
        int i = super.getTotalArmorValue() + 4;

        if (i > 20)
        {
            i = 20;
        }

        return i;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
    
    @Override
    public void onDeath(DamageSource cause)
    {
    	super.onDeath(cause);
    	
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, false);
    	
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        player.addChatMessage(new ChatComponentTranslation("chat.entity.GerKing.say2"));
    }
    
    @Override
    protected void dropFewItems(boolean arg1, int arg2)
    {
        this.dropItem(ItemLoader.gerHeart, 1);
        this.dropItem(ItemLoader.mjolnir, 1);
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
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
    	super.onInitialSpawn(difficulty, livingdata);
    	
        this.setCurrentItemOrArmor(0, new ItemStack(ItemLoader.mjolnir));
        
		return livingdata;
    }
    
    public float getEyeHeight()
    {
        float f = 1.74F;

        return f;
    }
}
