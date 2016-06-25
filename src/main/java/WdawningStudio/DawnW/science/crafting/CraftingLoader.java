package WdawningStudio.DawnW.science.crafting;

import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnet), new Object[]
        {
            "% &", "# #", "###", '%', new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), '&', Items.redstone, '#', ItemLoader.magnetIngot
        });
        GameRegistry.addRecipe(new ItemStack(BlockLoader.magnetBlock), new Object[]
        {
            "###", "###", "###", '#', ItemLoader.magnetIngot
        });
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetStick, 4), new Object[]
        {
            "#", "#", '#', ItemLoader.magnetIngot
        });
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetBall, 4), new Object[]
        {
            "#", '#', ItemLoader.magnetIngot
        });
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
		GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetSword), new Object[]
		{
				" # ", " # ", " * ", '#', ItemLoader.magnetIngot, '*', ItemLoader.magnetStick
		});
        GameRegistry.addRecipe(new ItemStack(ItemLoader.goldiamondSword), new Object[]
        {
                " % ", " & ", " * ", '%', Items.gold_ingot, '&', Items.diamond, '*', Items.stick
        });
        //
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetHelmet), new Object[]
        {
                "###", "# #", '#', ItemLoader.magnetIngot
        });
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetChestplate), new Object[]
        {
                "# #", "###", "###", '#', ItemLoader.magnetIngot
        });
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetLeggings), new Object[]
        {
                "###", "# #", "# #", '#', ItemLoader.magnetIngot
        });
        GameRegistry.addRecipe(new ItemStack(ItemLoader.magnetBoots), new Object[]
        {
                "# #", "# #", '#', ItemLoader.magnetIngot
        });
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