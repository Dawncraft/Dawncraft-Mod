package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
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
