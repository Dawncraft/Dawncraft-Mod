package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemMagnetStick extends Item
{
    public ItemMagnetStick()
    {
        super();
        this.setUnlocalizedName("magnetStick");
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
}
