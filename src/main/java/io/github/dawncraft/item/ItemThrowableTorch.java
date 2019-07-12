package io.github.dawncraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

import io.github.dawncraft.entity.projectile.EntityThrowableTorch;

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
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            EntityThrowableTorch entityThrowableTorch = new EntityThrowableTorch(world, player);
            world.spawnEntityInWorld(entityThrowableTorch);
            world.playSoundAtEntity(player, "random.bow", 0.8F, 0.5F);
            if (!player.capabilities.isCreativeMode)
            {
                --itemStack.stackSize;
            }
            player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }
        return itemStack;
    }
}
