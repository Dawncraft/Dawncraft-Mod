package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemMagnetIngot extends Item
{
    public ItemMagnetIngot()
    {
        super();
        this.setUnlocalizedName("magnetIngot");
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
}
