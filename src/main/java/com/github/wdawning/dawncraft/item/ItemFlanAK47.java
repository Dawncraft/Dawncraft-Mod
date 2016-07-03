package com.github.wdawning.dawncraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlanAK47 extends ItemFlan
{
    public ItemFlanAK47()
    {
        super(423);
        this.setUnlocalizedName("flanAK47");
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    { 
    	if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.arrow))
    	{
        if (!playerIn.capabilities.isCreativeMode)
        {
            itemStackIn.damageItem(1, playerIn);
            playerIn.inventory.consumeInventoryItem(Items.arrow);
        }
        
        worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityArrow(worldIn, playerIn, 4.0F));
        }
    	}
        
        return itemStackIn;
    }
}
