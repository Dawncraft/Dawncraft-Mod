package io.github.dawncraft.recipe;

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
 * Register some craft recipes.
 *
 * @author QingChenW
 */
public class CraftingLoader
{
    public static void initCrafting() {}

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
