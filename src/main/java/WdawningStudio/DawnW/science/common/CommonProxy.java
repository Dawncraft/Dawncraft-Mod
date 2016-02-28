package WdawningStudio.DawnW.science.common;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;
import WdawningStudio.DawnW.science.worldgen.WorldGeneratorLoader;
import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.crafting.CraftingLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new CreativeTabsLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new CraftingLoader();
        new WorldGeneratorLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}