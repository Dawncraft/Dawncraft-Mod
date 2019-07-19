package io.github.dawncraft.item;

import io.github.dawncraft.entity.projectile.EntityThrowableTorch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * A throwable torch which can burn the ground
 * <br>Come from Dead Cells</br>
 *
 * @author QingChenW
 * @author Viggo
 */
public class ItemThrowableTorch extends Item
{
    public ItemThrowableTorch()
    {
	super();
	this.setMaxStackSize(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
	ItemStack stack = player.getHeldItem(hand);
	if (!world.isRemote)
	{
	    EntityThrowableTorch entityThrowableTorch = new EntityThrowableTorch(world, player);
	    world.spawnEntity(entityThrowableTorch);
	    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.8F, 0.5F);
	    if (!player.capabilities.isCreativeMode)
	    {
		stack.shrink(1);
	    }
	    player.addStat(StatList.getObjectUseStats(this));
	    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
	return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}
