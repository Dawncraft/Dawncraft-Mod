package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;

/**
 * Register craft recipes.
 *
 * @author QingChenW
 */
public class CraftingLoader
{
    public static void initCrafting()
    {
        registerShapedOreRecipe("aaa", null, new ItemStack(ItemInit.MAGNET), new Object[]
                {
                        "B R", "A A", "AAA", 'A', "ingotMagnet", 'B', new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 'R', Items.REDSTONE
                });
        registerShapedOreRecipe("aab", null, new ItemStack(BlockInit.MAGNET_BLOCK), new Object[]
                {
                        "###", "###", "###", '#', "ingotMagnet"
                });
        registerShapelessRecipe("aac", null, new ItemStack(ItemInit.MAGNET_INGOT, 9), new ItemStack(BlockInit.MAGNET_BLOCK));
        registerShapedOreRecipe("aad", null, new ItemStack(ItemInit.MAGNET_STICK, 4), new Object[]
                {
                        "#", "#", '#', "ingotMagnet"
                });
        registerShapedOreRecipe("aaf", null, new ItemStack(ItemInit.MAGNET_BALL, 4), new Object[]
                {
                        "#", '#', "ingotMagnet"
                });
        registerShapelessOreRecipe("aag", null, new ItemStack(ItemInit.MAGNET_CARD, 1), new Object[]
                {
                        Items.PAPER, Items.PAPER, Items.PAPER, "ingotMagnet"
                });
        registerShapedOreRecipe("aah", null, new ItemStack(ItemInit.MAGNET_DOOR, 3), new Object[]
                {
                        "## ", "## ", "## ", '#', "ingotMagnet"
                });
        registerShapedRecipe("aai", null, new ItemStack(BlockInit.MAGNET_RAIL, 8), new Object[]
                {
                        "# #", "#*#", "#M#", '#', Items.IRON_INGOT , '*', Items.STICK, 'M', ItemInit.MAGNET
                });

        registerShapedRecipe("aaj", null, new ItemStack(BlockInit.SIMPLE_COMPUTER, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.IRON_INGOT
                });
        registerShapedRecipe("aak", null, new ItemStack(BlockInit.ADVANCED_COMPUTER, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.GOLD_INGOT
                });
        registerShapedRecipe("aal", null, new ItemStack(BlockInit.PROFESSIONAL_COMPUTER, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.DIAMOND
                });
        //Food

        //Tools
        registerShapedOreRecipe("aam", null, new ItemStack(ItemInit.MAGNET_SWORD), new Object[]
                {
                        " # ", " # ", " * ", '#', "ingotMagnet", '*', ItemInit.MAGNET_STICK
                });
        registerShapedOreRecipe("aan", null, new ItemStack(ItemInit.MAGNET_WAND), new Object[]
                {
                        "#*#", " * ", " * ", '#', "ingotMagnet", '*', ItemInit.MAGNET_STICK
                });
        registerShapedRecipe("aao", null, new ItemStack(ItemInit.GOLDIAMOND_SWORD), new Object[]
                {
                        " % ", " & ", " * ", '%', Items.GOLD_INGOT, '&', Items.DIAMOND, '*', Items.STICK
                });
        // Armors
        registerShapedOreRecipe("aap", null, new ItemStack(ItemInit.MAGNET_HELMET), new Object[]
                {
                        "###", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe("aaq", null, new ItemStack(ItemInit.MAGNET_CHESTPLATE), new Object[]
                {
                        "# #", "###", "###", '#', "ingotMagnet"
                });
        registerShapedOreRecipe("aar", null, new ItemStack(ItemInit.MAGNET_LEGGINGS), new Object[]
                {
                        "###", "# #", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe("aas", null, new ItemStack(ItemInit.MAGNET_BOOTS), new Object[]
                {
                        "# #", "# #", '#', "ingotMagnet"
                });
    }

    private static void registerShapedRecipe(String name, ResourceLocation group, ItemStack output, Object... params)
    {
        GameRegistry.addShapedRecipe(GameData.checkPrefix(name, true), group, output, params);
    }

    private static void registerShapelessRecipe(String name, ResourceLocation group, ItemStack output, ItemStack... params)
    {
        GameRegistry.addShapelessRecipe(GameData.checkPrefix(name, true), group, output, Ingredient.fromStacks(params));
    }

    private static void registerShapedOreRecipe(String name, ResourceLocation group, ItemStack output, Object... params)
    {
        registerRecipe(name, new ShapedOreRecipe(group, output, params));
    }

    private static void registerShapelessOreRecipe(String name, ResourceLocation group, ItemStack output, Object... params)
    {
        registerRecipe(name, new ShapelessOreRecipe(group, output, params));
    }

    private static void registerRecipe(String name, IRecipe recipe)
    {
        ForgeRegistries.RECIPES.register(recipe.setRegistryName(GameData.checkPrefix(name, true)));
    }
}
