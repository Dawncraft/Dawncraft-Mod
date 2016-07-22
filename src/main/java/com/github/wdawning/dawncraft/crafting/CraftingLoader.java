package com.github.wdawning.dawncraft.crafting;

import com.github.wdawning.dawncraft.block.BlockLoader;
import com.github.wdawning.dawncraft.item.ItemLoader;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingLoader
{
    public CraftingLoader()
    {
        registerRecipe();
        registerSmelting();
        registerFuel();
    }

    private static void registerRecipe()
    {
    	//Item
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnet), new Object[]
        {
            "% &", "# #", "###", '%', new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), '&', Items.redstone, '#', "ingotMagnet"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockLoader.magnetBlock), new Object[]
        {
            "###", "###", "###", '#', "ingotMagnet"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetStick, 4), new Object[]
        {
            "#", "#", '#', "ingotMagnet"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetBall, 4), new Object[]
        {
            "#", '#', "ingotMagnet"
        }));
        //Block
        GameRegistry.addShapelessRecipe(new ItemStack(ItemLoader.magnetIngot, 9), BlockLoader.magnetBlock);
        GameRegistry.addRecipe(new ItemStack(BlockLoader.simpleComputer, 2), new Object[]
        {
            "###", "# #", "###", '#', Items.iron_ingot
        });
        GameRegistry.addRecipe(new ItemStack(BlockLoader.highComputer, 2), new Object[]
        {
            "###", "# #", "###", '#', Items.gold_ingot
        });
        GameRegistry.addRecipe(new ItemStack(BlockLoader.proComputer, 2), new Object[]
        {
            "###", "# #", "###", '#', Items.diamond
        });
		        GameRegistry.addRecipe(new ItemStack(BlockLoader.superComputer, 2), new Object[]
        {
            "###", "# #", "###", '#', Items.emerald
        });
        //Food
        
        //Tools
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetSword), new Object[]
		{
				" # ", " # ", " * ", '#', "ingotMagnet", '*', ItemLoader.magnetStick
		}));
        GameRegistry.addRecipe(new ItemStack(ItemLoader.goldiamondSword), new Object[]
        {
                " % ", " & ", " * ", '%', Items.gold_ingot, '&', Items.diamond, '*', Items.stick
        });
        //
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetHelmet), new Object[]
        {
                "###", "# #", '#', "ingotMagnet"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetChestplate), new Object[]
        {
                "# #", "###", "###", '#', "ingotMagnet"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetLeggings), new Object[]
        {
                "###", "# #", "# #", '#', "ingotMagnet"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.magnetBoots), new Object[]
        {
                "# #", "# #", '#', "ingotMagnet"
        }));
    }

    private static void registerSmelting()
    {
        GameRegistry.addSmelting(BlockLoader.magnetOre, new ItemStack(ItemLoader.magnetIngot), 0.7F);
        GameRegistry.addSmelting(Items.egg, new ItemStack(ItemLoader.cakeEgg), 0.3F);
    }

    private static void registerFuel()
    {
        GameRegistry.registerFuelHandler(new IFuelHandler()
        {
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                return ItemLoader.bucketPetroleum != fuel.getItem() ? 0 : 25600;
            }
        });
    }
}