package com.github.wdawning.dawncraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemMagicBook extends Item
{
	public ItemMagicBook()
	{
		super();
		this.setUnlocalizedName("magicBook");
        this.setMaxStackSize(16);
		this.setCreativeTab(CreativeTabsLoader.tabMagic);
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
		return itemStackIn;
    }
}
