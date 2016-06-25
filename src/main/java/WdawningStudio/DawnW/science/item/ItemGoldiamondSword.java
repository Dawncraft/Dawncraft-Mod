package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

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