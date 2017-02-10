package io.github.dawncraft.common;

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
    public static CreativeTabs tabMagnetic;
    public static CreativeTabs tabMachine;
    public static CreativeTabs tabComputer;
    public static CreativeTabs tabMaterials;
    public static CreativeTabs tabFurniture;
    public static CreativeTabs tabFood;
    public static CreativeTabs tabMagic;
    public static CreativeTabs tabFlans;
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
        tabMagnetic = new CreativeTabs("Magnetic")
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
        tabMaterials = new CreativeTabs("TeMaterials")
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
                return Item.getItemFromBlock(BlockLoader.superChest);
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
        tabMagic = new CreativeTabs("Magic")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magicBook;
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