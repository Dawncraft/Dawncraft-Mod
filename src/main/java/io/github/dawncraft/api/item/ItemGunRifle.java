package io.github.dawncraft.api.item;

import io.github.dawncraft.item.ItemLoader;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 步枪等各种大型枪械
 *
 * @author QingChenW
 */
public class ItemGunRifle extends ItemGun
{
    private float shootDamage;
    
    public ItemGunRifle(int maxDamage, int clip, int reload, int interval, int velocity, double deviation, double sway, float damage)
    {
        this(maxDamage, clip, reload, interval, velocity, deviation, sway, 0.8F, damage);
    }
    
    public ItemGunRifle(int maxDamage, int clip, int reload, int interval, int velocity, double deviation, double sway, double moifier, float damage)
    {
        super(maxDamage, clip, reload, interval, velocity, deviation, sway, moifier);
        this.setDamage(damage);
    }
    
    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        if(count % this.getInterval() == 0)
        {
            if (player.capabilities.isCreativeMode || this.getAmmoAmount(stack) > 0)
            {
                if (!player.capabilities.isCreativeMode)
                {
                    stack.damageItem(1, player);
                    this.reduceAmmo(stack, 1, player);
                }

                player.worldObj.playSoundAtEntity(player, this.getShootSound(), 1.0F, 1.0F);

                if (!player.worldObj.isRemote)
                {
                    //EntityBullet entity = new EntityBullet(player.worldObj, player, 3.0F);
                    //entity.setDamage(this.getDamage());
                    EntityArrow entity = new EntityArrow(player.worldObj, player, 1.0F);
                    player.worldObj.spawnEntityInWorld(entity);
                }
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void renderSightOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks)
    {
        
    }

    @Override
    public Item getAmmo()
    {
        return ItemLoader.gunBullet;
    }

    public float getDamage()
    {
        return this.shootDamage;
    }
    
    public ItemGunRifle setDamage(float damage)
    {
        this.shootDamage = damage;
        return this;
    }
}
