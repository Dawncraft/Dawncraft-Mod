package io.github.dawncraft.creativetab;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register CreativeTabs in the class.
 *
 * @author QingChenW
 */
public class CreativeTabsLoader
{
    public static CreativeTabs tabEnergy;
    public static CreativeTabs tabMagnetism;
    public static CreativeTabs tabMachine;
    public static CreativeTabs tabComputer;
    public static CreativeTabs tabScience;
    public static CreativeTabs tabFurniture;
    public static CreativeTabs tabFood;
    public static CreativeTabs tabFlans;
    public static CreativeTabs tabMagic;
    public static CreativeTabs tabColourEgg;

    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
        tabEnergy = new CreativeTabs("Energy")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.bucketPetroleum;
            }
        };
        tabMagnetism = new CreativeTabs("Magnetism")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magnetIngot;
            }
        };
        tabMachine = new CreativeTabs("Machine")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.machineFurnace);
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
        tabScience = new CreativeTabs("Science")
        {
            @Override
            public Item getTabIconItem()
            {
                return Items.stick;
            }
        };
        tabFurniture = new CreativeTabs("Furniture")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.woodTable);
            }
        };
        tabFood = new CreativeTabs("Food")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.faeces;
            }
        };
        tabFlans = new CreativeTabs("Flans")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.flanRPG;
            }
        };
        tabMagic = new CreativeTabs("Magic")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magicDust;
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
