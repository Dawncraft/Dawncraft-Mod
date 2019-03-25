package io.github.dawncraft.recipe;

import net.minecraft.item.ItemStack;

import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

/**
 * Register Brewing.
 *
 * @author QingChenW
 */
public class BrewingLoader
{
    public static void initBrewing()
    {

    }
    
    private static void registerPotion(ItemStack input, ItemStack ingredient, ItemStack output)
    {
        BrewingRecipeRegistry.addRecipe(input, ingredient, output);
    }

    private static void registerOrePotion(ItemStack input, String ingredient, ItemStack output)
    {
        BrewingRecipeRegistry.addRecipe(input, ingredient, output);
    }

    private static void registerPotion(IBrewingRecipe recipe)
    {
        BrewingRecipeRegistry.addRecipe(recipe);
    }
}
