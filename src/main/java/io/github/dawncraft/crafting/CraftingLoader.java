package io.github.dawncraft.crafting;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.FishingHooks.FishableCategory;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Register recipes, smelting and fuels.
 *
 * @author QingChenW
 */
public class CraftingLoader
{
    public CraftingLoader(FMLInitializationEvent event)
    {
        // recipes
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnet), new Object[]
                {
                        "B R", "A A", "AAA", 'A', "ingotMagnet", 'B', new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 'R', Items.redstone
                });
        registerShapedOreRecipe(new ItemStack(BlockLoader.magnetBlock), new Object[]
                {
                        "###", "###", "###", '#', "ingotMagnet"
                });
        registerShapelessRecipe(new ItemStack(ItemLoader.magnetIngot, 9), new Object[]
                {
                        BlockLoader.magnetBlock
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetStick, 4), new Object[]
                {
                        "#", "#", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetBall, 4), new Object[]
                {
                        "#", '#', "ingotMagnet"
                });
        registerShapelessOreRecipe(new ItemStack(ItemLoader.magnetCard, 1), new Object[]
                {
                        Items.paper, Items.paper, Items.paper, "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetDoor, 3), new Object[]
                {
                        "## ", "## ", "## ", '#', "ingotMagnet"
                });
        registerShapedRecipe(new ItemStack(BlockLoader.magnetRail, 8), new Object[]
                {
                        "# #", "#*#", "#M#", '#', Items.iron_ingot , '*', Items.stick, 'M', ItemLoader.magnet
                });
        
        registerShapedRecipe(new ItemStack(BlockLoader.simpleComputer, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.iron_ingot
                });
        registerShapedRecipe(new ItemStack(BlockLoader.advancedComputer, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.gold_ingot
                });
        registerShapedRecipe(new ItemStack(BlockLoader.superComputer, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.diamond
                });
        //Food
        
        //Tools
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetSword), new Object[]
                {
                        " # ", " # ", " * ", '#', "ingotMagnet", '*', ItemLoader.magnetStick
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetWand), new Object[]
                {
                        "#*#", " * ", " * ", '#', "ingotMagnet", '*', ItemLoader.magnetStick
                });
        registerShapedRecipe(new ItemStack(ItemLoader.goldiamondSword), new Object[]
                {
                        " % ", " & ", " * ", '%', Items.gold_ingot, '&', Items.diamond, '*', Items.stick
                });
        // Armors
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetHelmet), new Object[]
                {
                        "###", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetChestplate), new Object[]
                {
                        "# #", "###", "###", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetLeggings), new Object[]
                {
                        "###", "# #", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemLoader.magnetBoots), new Object[]
                {
                        "# #", "# #", '#', "ingotMagnet"
                });
        
        registerSmelting(BlockLoader.magnetOre, new ItemStack(ItemLoader.magnetIngot), 0.7F);
        registerSmelting(Items.egg, new ItemStack(ItemLoader.cakeEgg), 0.3F);
        registerFuel(ItemLoader.bucketPetroleum, 25600);

        registerFish(FishableCategory.FISH, ItemLoader.frog, 10);
    }

    private static void registerShapedRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addShapedRecipe(output, params);
    }
    
    private static void registerShapelessRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addShapelessRecipe(output, params);
    }
    
    private static void registerShapedOreRecipe(ItemStack output, Object... params)
    {
        registerRecipe(new ShapedOreRecipe(output, params));
    }
    
    private static void registerShapelessOreRecipe(ItemStack output, Object... params)
    {
        registerRecipe(new ShapelessOreRecipe(output, params));
    }
    
    private static void registerRecipe(IRecipe recipe)
    {
        GameRegistry.addRecipe(recipe);
    }

    private static void registerSmelting(Item input, ItemStack output, float xp)
    {
        GameRegistry.addSmelting(input, output, xp);
    }
    
    private static void registerSmelting(Block input, ItemStack output, float xp)
    {
        GameRegistry.addSmelting(input, output, xp);
    }

    private static void registerFuel(final Item input, final int burnTime)
    {
        registerFuel(new IFuelHandler()
        {
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                return fuel.getItem() != input ? 0 : burnTime;
            }
        });
    }
    
    private static void registerFuel(final Block input, final int burnTime)
    {
        registerFuel(Item.getItemFromBlock(input), burnTime);
    }
    
    private static void registerFuel(IFuelHandler handler)
    {
        GameRegistry.registerFuelHandler(handler);
    }
    
    private static void registerFish(FishableCategory category, Item item, int weight)
    {
        registerFish(category, new ItemStack(item), weight, 0.0F, false);
    }
    
    private static void registerFish(FishableCategory category, Block block, int weight)
    {
        registerFish(category, new ItemStack(block), weight, 0.0F, false);
    }
    
    private static void registerFish(FishableCategory category, ItemStack itemstack, int weight, float maxDamagePercent, boolean enchantable)
    {
        WeightedRandomFishable item = new WeightedRandomFishable(itemstack, weight).setMaxDamagePercent(maxDamagePercent);
        if(enchantable) item.setEnchantable();
        switch(category)
        {
            case JUNK: FishingHooks.addJunk(item); break;
            case FISH: FishingHooks.addFish(item); break;
            case TREASURE: FishingHooks.addTreasure(item); break;
        }
    }
}
