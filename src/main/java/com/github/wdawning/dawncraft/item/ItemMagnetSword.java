package com.github.wdawning.dawncraft.item;

import net.minecraft.item.ItemSword;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemMagnetSword extends ItemSword
{
    public ItemMagnetSword()
    {
        super(ItemLoader.MAGNET);
        this.setUnlocalizedName("magnetSword");
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
}