package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Register craft recipes.
 *
 * @author QingChenW
 */
public class CraftingLoader
{
    public static void initCrafting()
    {
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET), new Object[]
                {
                        "B R", "A A", "AAA", 'A', "ingotMagnet", 'B', new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 'R', Items.REDSTONE
                });
        registerShapedOreRecipe(new ItemStack(BlockInit.MAGNET_BLOCK), new Object[]
                {
                        "###", "###", "###", '#', "ingotMagnet"
                });
        registerShapelessRecipe(new ItemStack(ItemInit.MAGNET_INGOT, 9), new ItemStack(BlockInit.MAGNET_BLOCK));
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_STICK, 4), new Object[]
                {
                        "#", "#", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_BALL, 4), new Object[]
                {
                        "#", '#', "ingotMagnet"
                });
        registerShapelessOreRecipe(new ItemStack(ItemInit.MAGNET_CARD, 1), new Object[]
                {
                        Items.PAPER, Items.PAPER, Items.PAPER, "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_DOOR, 3), new Object[]
                {
                        "## ", "## ", "## ", '#', "ingotMagnet"
                });
        registerShapedRecipe(new ItemStack(BlockInit.MAGNET_RAIL, 8), new Object[]
                {
                        "# #", "#*#", "#M#", '#', Items.IRON_INGOT , '*', Items.STICK, 'M', ItemInit.MAGNET
                });

        registerShapedRecipe(new ItemStack(BlockInit.SIMPLE_COMPUTER, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.IRON_INGOT
                });
        registerShapedRecipe(new ItemStack(BlockInit.ADVANCED_COMPUTER, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.GOLD_INGOT
                });
        registerShapedRecipe(new ItemStack(BlockInit.PROFESSIONAL_COMPUTER, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.DIAMOND
                });
        //Food

        //Tools
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_SWORD), new Object[]
                {
                        " # ", " # ", " * ", '#', "ingotMagnet", '*', ItemInit.MAGNET_STICK
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_WAND), new Object[]
                {
                        "#*#", " * ", " * ", '#', "ingotMagnet", '*', ItemInit.MAGNET_STICK
                });
        registerShapedRecipe(new ItemStack(ItemInit.GOLDIAMOND_SWORD), new Object[]
                {
                        " % ", " & ", " * ", '%', Items.GOLD_INGOT, '&', Items.DIAMOND, '*', Items.STICK
                });
        // Armors
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_HELMET), new Object[]
                {
                        "###", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_CHESTPLATE), new Object[]
                {
                        "# #", "###", "###", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_LEGGINGS), new Object[]
                {
                        "###", "# #", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.MAGNET_BOOTS), new Object[]
                {
                        "# #", "# #", '#', "ingotMagnet"
                });
    }

    private static void registerShapedRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addShapedRecipe(null, null, output, params);
    }

    private static void registerShapelessRecipe(ItemStack output, ItemStack... params)
    {
        GameRegistry.addShapelessRecipe(null, null, output, Ingredient.fromStacks(params));
    }

    private static void registerShapedOreRecipe(ItemStack output, Object... params)
    {
        registerRecipe(new ShapedOreRecipe(null, output, params));
    }

    private static void registerShapelessOreRecipe(ItemStack output, Object... params)
    {
        registerRecipe(new ShapelessOreRecipe(null, output, params));
    }

    private static void registerRecipe(IRecipe recipe)
    {
        ForgeRegistries.RECIPES.register(recipe);
    }
}
