package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.entity.EntityFlanBomb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlanRPG extends ItemFlan
{
    public ItemFlanRPG()
    {
        super();
        this.setUnlocalizedName("flanRPG");
        this.setMaxDamage(28);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    { 
    	if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ItemLoader.flanRPGRocket))
    	{
        if (!playerIn.capabilities.isCreativeMode)
        {
            itemStackIn.damageItem(1, playerIn);
            playerIn.inventory.consumeInventoryItem(ItemLoader.flanRPGRocket);
        }
        
        worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityFlanBomb(worldIn, playerIn));
        }
    	}
        
        return itemStackIn;
    }
}