package io.github.dawncraft.item;

import io.github.dawncraft.entity.projectile.EntityMagnetBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Magnet ball.
 *
 * @author QingChenW
 */
public class ItemMagnetBall extends Item
{
    public ItemMagnetBall()
    {
        super();
        this.setMaxStackSize(16);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            BlockPos blockPos = world.getStrongholdPos("", new BlockPos(player));
            
            if (blockPos != null)
            {
                EntityMagnetBall entityMagnetBall = new EntityMagnetBall(world, player);
                entityMagnetBall.moveTowards(blockPos);
                world.spawnEntityInWorld(entityMagnetBall);
                world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                if (!player.capabilities.isCreativeMode)
                {
                    --itemStack.stackSize;
                }
                player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
}
