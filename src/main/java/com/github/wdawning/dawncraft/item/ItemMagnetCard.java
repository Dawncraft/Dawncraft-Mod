package com.github.wdawning.dawncraft.item;

import net.minecraft.item.Item;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemMagnetCard extends Item
{
    public ItemMagnetCard()
	{
    	super();
    	this.setUnlocalizedName("magnetCard");
        this.setMaxStackSize(16);
    	this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
	}
}