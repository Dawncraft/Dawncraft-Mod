/**
 *
 */
package io.github.dawncraft.api.item;

import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.gui.GuiIngameDawn;
import io.github.dawncraft.entity.projectile.EntityRocket;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 火箭筒等发射导弹的枪械
 *
 * @author QingChenW
 */
public class ItemGunLauncher extends ItemGun
{
    public ItemGunLauncher(int maxDamage, int clip, int reload, int interval, int velocity, double deviation, double sway, double moifier)
    {
        super(maxDamage, clip, reload, interval, velocity, deviation, sway, moifier);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        if (playerIn.capabilities.isCreativeMode || this.getAmmoAmount(itemStackIn) > 0)
        {
            int power = this.getMaxItemUseDuration(itemStackIn) - timeLeft;
            
            if (!playerIn.capabilities.isCreativeMode)
            {
                itemStackIn.damageItem(1, playerIn);
                this.reduceAmmo(itemStackIn, 1, playerIn);
            }
            
            worldIn.playSoundAtEntity(playerIn, this.getShootSound(), 1.0F, 1.0F);
            
            if (!worldIn.isRemote)
            {
                try
                {
                    Entity entity = this.getAmmoEntity().getConstructor(World.class, EntityLivingBase.class, float.class, float.class).newInstance(worldIn, playerIn, 1.5F, 2.0F);
                    worldIn.spawnEntityInWorld(entity);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderSightOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks)
    {
        ClientProxy.getIngameGUIDawn().bind(GuiIngameDawn.icons);
        ClientProxy.getIngameGUIDawn().drawTexturedModalRect(resolution.getScaledWidth() / 2 - 20, resolution.getScaledHeight() / 2 - 20, 0, 129, 41, 41);
    }
    
    @Override
    public Item getAmmo()
    {
        return ItemLoader.gunRocket;
    }

    public Class<? extends Entity> getAmmoEntity()
    {
        return EntityRocket.class;
    }
}
