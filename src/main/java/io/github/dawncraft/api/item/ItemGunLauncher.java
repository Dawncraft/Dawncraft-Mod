package io.github.dawncraft.api.item;

import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.gui.GuiIngameDawn;
import io.github.dawncraft.entity.projectile.EntityRocket;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
	if (entityLiving instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) entityLiving;
	    if (player.capabilities.isCreativeMode || this.getAmmoAmount(stack) > 0)
	    {
		int power = this.getMaxItemUseDuration(stack) - timeLeft;

		if (!player.capabilities.isCreativeMode)
		{
		    stack.damageItem(1, player);
		    this.reduceAmmo(stack, 1, player);
		}

		player.playSound(this.getShootSound(), 1.0F, 1.0F);

		if (!world.isRemote)
		{
		    try
		    {
			Entity entity = this.getAmmoEntity().getConstructor(World.class, EntityLivingBase.class, float.class, float.class).newInstance(world, player, 1.5F, 2.0F);
			world.spawnEntity(entity);
		    }
		    catch (Exception e)
		    {
			e.printStackTrace();
		    }
		}
	    }
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderSightOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks)
    {
	ClientProxy.getInstance().getIngameGUIDawn().bind(GuiIngameDawn.ICONS);
	ClientProxy.getInstance().getIngameGUIDawn().drawTexturedModalRect(resolution.getScaledWidth() / 2 - 20, resolution.getScaledHeight() / 2 - 20, 0, 129, 41, 41);
    }

    @Override
    public ItemStack getAmmo()
    {
	return new ItemStack(ItemInit.gunRocket);
    }

    public Class<? extends Entity> getAmmoEntity()
    {
	return EntityRocket.class;
    }
}
