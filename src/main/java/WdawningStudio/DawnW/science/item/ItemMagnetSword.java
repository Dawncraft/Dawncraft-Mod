package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

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