package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.ItemSword;

public class ItemMagnetSword extends ItemSword
{
    public ItemMagnetSword()
    {
        super(ItemLoader.MAGNET);
        this.setUnlocalizedName("magnetSword");
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
}