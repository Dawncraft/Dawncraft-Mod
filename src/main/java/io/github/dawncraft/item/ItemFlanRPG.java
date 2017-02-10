package io.github.dawncraft.item;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlanRPG extends ItemFlan
{
    public ItemFlanRPG(int maxDamage)
    {
        super(28);
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
            worldIn.spawnEntityInWorld(new EntityTNTPrimed(worldIn, 0, 0.2, 0, playerIn));//TODO FlanBomb火箭炮投掷物
        }
    	}
        
        return itemStackIn;
    }
}