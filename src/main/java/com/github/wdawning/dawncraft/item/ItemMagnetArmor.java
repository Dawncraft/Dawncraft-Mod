package com.github.wdawning.dawncraft.item;

import net.minecraft.item.ItemArmor;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemMagnetArmor extends ItemArmor
{
    public ItemMagnetArmor(int armorType)
    {
        super(ItemLoader.MAGNET_ARMOR, ItemLoader.MAGNET_ARMOR.ordinal(), armorType);
    }

    public static class Helmet extends ItemMagnetArmor
    {
        public Helmet()
        {
            super(0);
            this.setUnlocalizedName("magnetHelmet");
            this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
        }
    }

    public static class Chestplate extends ItemMagnetArmor
    {
        public Chestplate()
        {
            super(1);
            this.setUnlocalizedName("magnetChestplate");
            this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
        }
    }

    public static class Leggings extends ItemMagnetArmor
    {
        public Leggings()
        {
            super(2);
            this.setUnlocalizedName("magnetLeggings");
            this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
        }
    }

    public static class Boots extends ItemMagnetArmor
    {
        public Boots()
        {
            super(3);
            this.setUnlocalizedName("magnetBoots");
            this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
        }
    }
}