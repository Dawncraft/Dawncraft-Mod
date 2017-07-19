package io.github.dawncraft.item;

import io.github.dawncraft.api.item.ItemFlanBase;
import io.github.dawncraft.entity.projectile.EntityBullet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlanAK47 extends ItemFlanBase
{
    public ItemFlanAK47(int maxDamage)
    {
        super(maxDamage);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    { 
    	if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ItemLoader.flanBullet))
    	{
        if (!playerIn.capabilities.isCreativeMode)
        {
            itemStackIn.damageItem(1, playerIn);
            playerIn.inventory.consumeInventoryItem(ItemLoader.flanBullet);
        }
        
        worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityBullet(worldIn, playerIn, 3.0F, 1.0F));
        }
    	}
        
        return itemStackIn;
    }
}
