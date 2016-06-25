package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

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
