package WdawningStudio.DawnW.science.creativetab;

import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsLoader
{
    public static CreativeTabs tabFurniture;
    public static CreativeTabs tabEnergy;
    public static CreativeTabs tabMagnetic;
    public static CreativeTabs tabComputer;
    public static CreativeTabs tabRobot;
    public static CreativeTabs tabFood;
    public static CreativeTabs tabColourEgg;

    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
    	tabFurniture = new CreativeTabs("Furniture")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.wchest);
            }
        };	
    	tabEnergy = new CreativeTabs("Energy")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.bucketPetroleum;
            }
        };
    	tabMagnetic = new CreativeTabs("Magnetic")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magnetIngot;
            }
        }; 
    	tabComputer = new CreativeTabs("Computer")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.simpleComputer);
            }
        };
    	tabRobot = new CreativeTabs("Robot")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.simpleComputer);
            }
        };
    	tabFood = new CreativeTabs("Food")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.cakeEgg;
            }
        };
    	tabColourEgg = new CreativeTabs("ColourEgg")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.goldiamondSword;
            }
        };
    }
}