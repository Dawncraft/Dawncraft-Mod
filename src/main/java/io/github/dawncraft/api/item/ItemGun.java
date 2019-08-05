package io.github.dawncraft.api.item;

import io.github.dawncraft.api.event.entity.BulletNockEvent;
import io.github.dawncraft.client.sound.SoundInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The basic gun class.
 *
 * @author QingChenW
 */
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
    public EnumAction getItemUseAction(ItemStack stack)
    {
        if (this.getAmmoAmount(stack) > 0)
        {
            return ItemInit.SHOOT;
        }
        return super.getItemUseAction(stack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (state.getBlockHardness(world, pos) != 0.0F)
        {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }

    /**
     * 当枪械第一次使用时
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        BulletNockEvent event = new BulletNockEvent(player, stack);
        if (MinecraftForge.EVENT_BUS.post(event)) return new ActionResult<>(EnumActionResult.FAIL, event.result);
        if (player.capabilities.isCreativeMode || this.getAmmoAmount(stack) > 0)
        {
            player.setActiveHand(hand);
        }
        else if (this.getAmmo() != null || player.inventory.hasItemStack(this.getAmmo()))
        {
            // 装弹逻辑
            this.setAmmoAmount(stack, this.getClip());
            player.playSound(this.getReloadSound(), 1.0F, 1.0F);
        }
        else
        {
            player.playSound(this.getEmptySound(), 1.0F, 1.0F);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    /**
     * 当枪械一直在使用时
     */
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count)
    {
    }

    /**
     * 玩家停止使用枪械时
     * 火箭筒之类的应该重写这个
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
    }

    /**
     * 玩家使用完枪械时
     * 步枪之类的应该重写这个
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
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
    public void renderSightOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks){}

    /**
     * @return 未装弹射击音效
     */
    public SoundEvent getEmptySound()
    {
        return SoundInit.GUN_EMPTY;
    }

    /**
     * @return 装弹音效
     */
    public SoundEvent getReloadSound()
    {
        return SoundInit.GUN_RELOAD;
    }

    /**
     * @return 射击音效
     */
    public SoundEvent getShootSound()
    {
        return SoundInit.GUN_SHOOT;
    }

    /**
     * 获取弹药物品
     *
     * @return
     */
    public ItemStack getAmmo()
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
    public void reduceAmmo(ItemStack stack, int amount, EntityLivingBase entity)
    {
        if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode)
        {
            int clip = this.getAmmoAmount(stack);
            this.setAmmoAmount(stack, clip - amount);
            // this.renderBulletShells(stack, entity);
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
