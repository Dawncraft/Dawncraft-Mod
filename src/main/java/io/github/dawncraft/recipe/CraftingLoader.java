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
        registerShapedOreRecipe(new ItemStack(ItemInit.magnet), new Object[]
                {
                        "B R", "A A", "AAA", 'A', "ingotMagnet", 'B', new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 'R', Items.REDSTONE
                });
        registerShapedOreRecipe(new ItemStack(BlockInit.magnetBlock), new Object[]
                {
                        "###", "###", "###", '#', "ingotMagnet"
                });
        registerShapelessRecipe(new ItemStack(ItemInit.magnetIngot, 9), new Object[]
                {
                        BlockInit.magnetBlock
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetStick, 4), new Object[]
                {
                        "#", "#", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetBall, 4), new Object[]
                {
                        "#", '#', "ingotMagnet"
                });
        registerShapelessOreRecipe(new ItemStack(ItemInit.magnetCard, 1), new Object[]
                {
                        Items.PAPER, Items.PAPER, Items.PAPER, "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetDoor, 3), new Object[]
                {
                        "## ", "## ", "## ", '#', "ingotMagnet"
                });
        registerShapedRecipe(new ItemStack(BlockInit.magnetRail, 8), new Object[]
                {
                        "# #", "#*#", "#M#", '#', Items.IRON_INGOT , '*', Items.STICK, 'M', ItemInit.magnet
                });

        registerShapedRecipe(new ItemStack(BlockInit.simpleComputer, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.IRON_INGOT
                });
        registerShapedRecipe(new ItemStack(BlockInit.advancedComputer, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.GOLD_INGOT
                });
        registerShapedRecipe(new ItemStack(BlockInit.superComputer, 2), new Object[]
                {
                        "###", "# #", "###", '#', Items.DIAMOND
                });
        //Food

        //Tools
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetSword), new Object[]
                {
                        " # ", " # ", " * ", '#', "ingotMagnet", '*', ItemInit.magnetStick
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetWand), new Object[]
                {
                        "#*#", " * ", " * ", '#', "ingotMagnet", '*', ItemInit.magnetStick
                });
        registerShapedRecipe(new ItemStack(ItemInit.goldiamondSword), new Object[]
                {
                        " % ", " & ", " * ", '%', Items.GOLD_INGOT, '&', Items.DIAMOND, '*', Items.STICK
                });
        // Armors
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetHelmet), new Object[]
                {
                        "###", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetChestplate), new Object[]
                {
                        "# #", "###", "###", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetLeggings), new Object[]
                {
                        "###", "# #", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInit.magnetBoots), new Object[]
                {
                        "# #", "# #", '#', "ingotMagnet"
                });
    }

    private static void registerShapedRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addShapedRecipe(null, null, output, params);
    }

    private static void registerShapelessRecipe(ItemStack output, Ingredient... params)
    {
        GameRegistry.addShapelessRecipe(null, null, output, params);
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
