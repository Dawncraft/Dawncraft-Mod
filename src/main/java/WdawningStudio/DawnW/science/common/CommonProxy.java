package WdawningStudio.DawnW.science.common;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.entity.EntityLoader;
import WdawningStudio.DawnW.science.fluid.FluidLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;
import WdawningStudio.DawnW.science.potion.PotionLoader;
import WdawningStudio.DawnW.science.worldgen.WorldGeneratorLoader;
import WdawningStudio.DawnW.science.achievement.AchievementLoader;
import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.crafting.CraftingLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new FluidLoader(event);
        new ConfigLoader(event);
        new CreativeTabsLoader(event);
        new BlockLoader(event);
        new ItemLoader(event);
        new PotionLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new EventLoader();
        new CraftingLoader();
        new EntityLoader();
        new WorldGeneratorLoader();
        new AchievementLoader();
        //    new EnchantmentLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}