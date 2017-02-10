package io.github.dawncraft.entity;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

/**
 * ger king
 * 
 * @author QingChenW
 * @author XiaoLang_dada and 4561789
 */
public class EntityGerKing extends EntityCreature implements IBossDisplayData,IMob
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
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }
    
    @Override
    public float getEyeHeight()
    {
        return 1.74F;
    }
    
    @Override
    protected String getLivingSound()
    {
        return dawncraft.MODID + ":" + "mob.gerking.say";
    }
    
    @Override
    protected String getHurtSound()
    {
        return dawncraft.MODID + ":" + "mob.gerking.hurt";
    }
    
    @Override
    protected String getDeathSound()
    {
        return dawncraft.MODID + ":" + "mob.gerking.death";
    }
    
    @Override
    protected Item getDropItem()
    {
        return null;
    }
    
    @Override
    protected void dropFewItems(boolean arg1, int arg2)
    {
        this.dropItem(ItemLoader.cakeEgg, 1);
        super.dropFewItems(arg1, arg2);
    }
    
    @Override
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
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        super.onInitialSpawn(difficulty, livingdata);
        
//        this.setCurrentItemOrArmor(0, new ItemStack(ItemLoader.mjolnir));
        
        return livingdata;
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
        
        if (!this.worldObj.isRemote)
        {
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, false);
            
            EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 64);
            // Minecraft.getMinecraft().thePlayer
            player.addChatMessage(new ChatComponentTranslation("chat.entity.GerKing.say2"));
        }
    }
}
