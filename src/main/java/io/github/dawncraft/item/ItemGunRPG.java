package io.github.dawncraft.item;

import io.github.dawncraft.api.event.entity.BulletNockEvent;
import io.github.dawncraft.api.item.ItemGun;
import io.github.dawncraft.entity.projectile.EntityRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunRPG extends ItemGun
{
    public ItemGunRPG(int maxDamage)
    {
        super(28);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        BulletNockEvent event = new BulletNockEvent(playerIn, itemStackIn);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return event.result;
        
        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ItemLoader.gunRocket))
        {
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        }
        
        return itemStackIn;
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ItemLoader.gunRocket))
        {
            int i = this.getMaxItemUseDuration(itemStackIn) - timeLeft;
            if (!playerIn.capabilities.isCreativeMode)
            {
                itemStackIn.damageItem(1, playerIn);
                playerIn.inventory.consumeInventoryItem(ItemLoader.gunRocket);

            }
            worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote)
            {
                EntityRocket entitybomb = new EntityRocket(worldIn, playerIn, 1.5F, 2.0F);
                worldIn.spawnEntityInWorld(entitybomb);
            }
        }
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 36000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
}