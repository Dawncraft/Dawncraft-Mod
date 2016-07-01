package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.item.ItemSword;

public class ItemGoldiamondSword extends ItemSword
{
    public ItemGoldiamondSword()
    {
        super(ItemLoader.GOLDIAMOND);
        this.setUnlocalizedName("goldiamondSword");
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
    }
}