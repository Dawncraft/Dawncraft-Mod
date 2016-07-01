package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemFlanRPGRocket extends Item
{
    public ItemFlanRPGRocket()
    {
        super();
        this.setUnlocalizedName("flanRPGRocket");
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabsLoader.tabFlans);
    }
}