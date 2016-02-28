package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemMagnet_ingot extends Item
{
    public ItemMagnet_ingot()
    {
        super();
        this.setUnlocalizedName("magnetIngot");
        this.setCreativeTab(CreativeTabsLoader.tabMagneticAndElectricity);
    }
}
