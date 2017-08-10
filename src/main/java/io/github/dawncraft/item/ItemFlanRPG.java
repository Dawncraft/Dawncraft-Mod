package io.github.dawncraft.item;

import io.github.dawncraft.entity.projectile.EntityRocket;
import io.github.dawncraft.event.api.BulletNockEvent;
import io.github.dawncraft.item.base.ItemFlanBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFlanRPG extends ItemFlanBase
{
    public ItemFlanRPG(int maxDamage)
    {
        super(28);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    { 
        BulletNockEvent event = new BulletNockEvent(playerIn, itemStackIn);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return event.result;
        
    	if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ItemLoader.flanRocket))
    	{
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
    	}
        
        return itemStackIn;
    }
    
    public void onPlayerStoppedUsing(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ItemLoader.flanRocket))
        {
            int i = this.getMaxItemUseDuration(itemStackIn) - timeLeft;
            EntityRocket entitybomb = new EntityRocket(worldIn, playerIn, 1.5F, 2.0F);
    		if (!playerIn.capabilities.isCreativeMode)
    		{
                itemStackIn.damageItem(1, playerIn);
    			playerIn.inventory.consumeInventoryItem(ItemLoader.flanRocket);

    		}
    		worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(entitybomb);
            }
        }
    }
    
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 36000;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
}