package io.github.dawncraft.api.item;

import io.github.dawncraft.Dawncraft;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGun extends Item
{
    private int clipAmount;
    private int reloadTicks;
    private int shootInterval;
    private double shootSway;
    private int ammoVelocity;
    private double ammoDeviation;
    private double sneakMoifier;
    
    public ItemGun(int maxDamage, int clip, int reload, int interval, int velocity, double deviation, double sway)
    {
        this(maxDamage, clip, reload, interval, velocity, deviation, sway, 0.75F);
    }
    
    public ItemGun(int maxDamage, int clip, int reload, int interval, int velocity, double deviation, double sway, double moifier)
    {
        this.setFull3D();
        this.setMaxStackSize(1);
        this.setMaxDamage(maxDamage);
        this.setClip(clip);
        this.setReload(reload);
        this.setInterval(interval);
        this.setVelocity(velocity);
        this.setDeviation(deviation);
        this.setSway(sway);
        this.setMoifier(moifier);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn)
    {
        if ((double)blockIn.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, playerIn);
        }

        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * 当枪械第一次使用时
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        //BulletNockEvent event = new BulletNockEvent(playerIn, itemStackIn);
        //if (MinecraftForge.EVENT_BUS.post(event)) return event.result;

        if (playerIn.capabilities.isCreativeMode || this.getAmmoAmount(itemStackIn) > 0)
        {
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        }
        else if(this.getAmmo() == null || playerIn.inventory.hasItem(this.getAmmo()))
        {
            // 装弹逻辑
            this.setAmmoAmount(itemStackIn, this.getClip());
            playerIn.playSound(this.getReloadSound(), 1.0F, 1.0F);
        }
        else
        {
            playerIn.playSound(this.getEmptySound(), 1.0F, 1.0F);
        }
        return itemStackIn;
    }
    
    /**
     * 当枪械一直在使用时
     */
    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    }
    
    /**
     * 玩家停止使用枪械时
     * 火箭筒之类的应该重写这个
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
    }
    
    /**
     * 玩家使用完枪械时
     * 步枪之类的应该重写这个
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        return stack;
    }
    
    /**
     * 渲染十字准星
     *
     * @param stack
     * @param player
     * @param resolution
     * @param partialTicks
     */
    @SideOnly(Side.CLIENT)
    public void renderSightOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks)
    {
        
    }
    
    /**
     * @return 未装弹射击音效
     */
    public String getEmptySound()
    {
        return Dawncraft.MODID + ":" + "gun.basic.empty";
    }
    
    /**
     * @return 装弹音效
     */
    public String getReloadSound()
    {
        return Dawncraft.MODID + ":" + "gun.basic.reload";
    }
    
    /**
     * @return 射击音效
     */
    public String getShootSound()
    {
        return Dawncraft.MODID + ":" + "gun.basic.shoot";
    }
    
    /**
     * 获取弹药物品
     *
     * @return
     */
    public Item getAmmo()
    {
        return null;
    }
    
    /**
     * 获取弹夹容量
     *
     * @return
     */
    public int getClip()
    {
        return this.clipAmount;
    }
    
    /**
     * 设置弹夹容量
     *
     * @param amount
     */
    public void setClip(int amount)
    {
        this.clipAmount = amount;
    }
    
    /**
     * 获取当前物品的弹药数量
     *
     * @param stack
     * @return
     */
    public int getAmmoAmount(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            return stack.getTagCompound().getInteger("ammo");
        }
        return 0;
    }

    /**
     * 设置当前物品的弹药数量
     *
     * @param stack
     * @param amount
     */
    public void setAmmoAmount(ItemStack stack, int amount)
    {
        if(amount < 0) amount = 0;
        stack.setTagInfo("ammo", new NBTTagInt(amount));
    }
    
    /**
     * 为一个枪械的弹药数量减一
     */
    public void reduceAmmo(ItemStack stack, int amount, EntityLivingBase entityIn)
    {
        if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).capabilities.isCreativeMode)
        {
            int clip = this.getAmmoAmount(stack);
            this.setAmmoAmount(stack, clip - amount);
            // this.renderBulletShells(stack, entityIn);
        }
    }
    
    /**
     * 获取装弹时间刻
     *
     * @return
     */
    public int getReload()
    {
        return this.reloadTicks;
    }
    
    /**
     * 设置装弹时间刻
     *
     * @param ticks
     */
    public void setReload(int ticks)
    {
        this.reloadTicks = ticks;
    }
    
    /**
     * 获取射击间隔时间刻
     *
     * @return
     */
    public int getInterval()
    {
        return this.shootInterval;
    }
    
    /**
     * 设置射击间隔时间刻
     *
     * @param ticks
     */
    public void setInterval(int ticks)
    {
        this.shootInterval = ticks;
    }
    
    /**
     * 获取视角晃动倍数
     *
     * @return
     */
    public double getSway()
    {
        return this.shootSway;
    }
    
    /**
     * 设置视角晃动倍数
     *
     * @param moifier
     */
    public void setSway(double moifier)
    {
        this.shootSway = moifier;
    }
    
    /**
     * 获取弹药发射速度
     *
     * @return
     */
    public int getVelocity()
    {
        return this.ammoVelocity;
    }
    
    /**
     * 设置弹药发射速度
     *
     * @param speed
     */
    public void setVelocity(int speed)
    {
        this.ammoVelocity = speed;
    }
    
    /**
     * 获取弹药发射偏差倍数
     * <br>现实世界中偏差是阻力造成的,不过我的世界又没有风这种东西</br>
     *
     * @return
     */
    public double getDeviation()
    {
        return this.ammoDeviation;
    }
    
    /**
     * 设置弹药发射偏差倍数
     *
     * @param moifier
     */
    public void setDeviation(double moifier)
    {
        this.ammoDeviation = moifier;
    }
    
    /**
     * 获取潜行误差补偿倍数
     * <br>蹲下的时候可以打得准点嘛</br>
     *
     * @return
     */
    public double getMoifier()
    {
        return this.sneakMoifier;
    }
    
    /**
     * 设置潜行误差补偿倍数
     *
     * @param moifier
     */
    public void setMoifier(double moifier)
    {
        this.sneakMoifier = moifier;
    }
}
