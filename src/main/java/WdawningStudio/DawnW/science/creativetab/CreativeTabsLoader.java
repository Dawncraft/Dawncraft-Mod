package WdawningStudio.DawnW.science.creativetab;

import WdawningStudio.DawnW.science.item.ItemLoader;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsLoader
{
    public static CreativeTabs tabMagneticAndElectricity;
    public static CreativeTabs tabColourEgg;

    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
    	tabMagneticAndElectricity = new CreativeTabs("MagneticAndElectricity")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magnet_ingot;
            }
        };
    	tabMagneticAndElectricity = new CreativeTabs("ColourEgg")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magnet_ingot;
            }
        };
    }
}