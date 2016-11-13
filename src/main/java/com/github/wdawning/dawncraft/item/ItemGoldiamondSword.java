package com.github.wdawning.dawncraft.item;

import net.minecraft.item.ItemSword;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemGoldiamondSword extends ItemSword
{
    public ItemGoldiamondSword()
    {
        super(ItemLoader.GOLDIAMOND);
        this.setUnlocalizedName("goldiamondSword");
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
    }
}