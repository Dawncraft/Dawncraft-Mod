package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemMagnet extends Item
{
	public ItemMagnet()
	{
    super();
    this.setUnlocalizedName("magnet");
    this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
	}
}
