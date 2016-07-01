package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemMagnetCard extends Item
{
    public ItemMagnetCard()
	{
    	super();
    	this.setUnlocalizedName("magnetCard");
        this.maxStackSize = 16;
    	this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
	}
}