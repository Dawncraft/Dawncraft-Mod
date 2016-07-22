package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.github.wdawning.dawncraft.entity.EntityMagnetBall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagnetBall extends Item
{
    public ItemMagnetBall()
    {
        super();
        this.setUnlocalizedName("magnetBall");
        this.setMaxStackSize(16);
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }
        
        worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityMagnetBall(worldIn, playerIn));
        }
        
        return itemStackIn;
    }
}
