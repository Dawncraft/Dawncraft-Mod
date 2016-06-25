package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import net.minecraft.item.Item;

public class ItemMagnet extends Item
{
	public ItemMagnet()
	{
    super();
    this.setUnlocalizedName("magnet");
    this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
	}
}
