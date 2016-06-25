package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import net.minecraft.item.ItemFood;

public class ItemCakeEgg extends ItemFood
{
    public ItemCakeEgg()
    {
        super(4, 6.0F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("cakeEgg");
        this.setCreativeTab(CreativeTabsLoader.tabFood);
    }
}