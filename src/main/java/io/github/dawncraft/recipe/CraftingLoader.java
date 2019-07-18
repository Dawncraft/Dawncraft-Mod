package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemInitializer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnet), new Object[]
                {
                        "B R", "A A", "AAA", 'A', "ingotMagnet", 'B', new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 'R', Items.redstone
                });
        registerShapedOreRecipe(new ItemStack(BlockLoader.magnetBlock), new Object[]
                {
                        "###", "###", "###", '#', "ingotMagnet"
                });
        registerShapelessRecipe(new ItemStack(ItemInitializer.magnetIngot, 9), new Object[]
                {
                        BlockLoader.magnetBlock
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetStick, 4), new Object[]
                {
                        "#", "#", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetBall, 4), new Object[]
                {
                        "#", '#', "ingotMagnet"
                });
        registerShapelessOreRecipe(new ItemStack(ItemInitializer.magnetCard, 1), new Object[]
                {
                        Items.paper, Items.paper, Items.paper, "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetDoor, 3), new Object[]
                {
                        "## ", "## ", "## ", '#', "ingotMagnet"
                });
        registerShapedRecipe(new ItemStack(BlockLoader.magnetRail, 8), new Object[]
                {
                        "# #", "#*#", "#M#", '#', Items.iron_ingot , '*', Items.stick, 'M', ItemInitializer.magnet
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
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetSword), new Object[]
                {
                        " # ", " # ", " * ", '#', "ingotMagnet", '*', ItemInitializer.magnetStick
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetWand), new Object[]
                {
                        "#*#", " * ", " * ", '#', "ingotMagnet", '*', ItemInitializer.magnetStick
                });
        registerShapedRecipe(new ItemStack(ItemInitializer.goldiamondSword), new Object[]
                {
                        " % ", " & ", " * ", '%', Items.gold_ingot, '&', Items.diamond, '*', Items.stick
                });
        // Armors
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetHelmet), new Object[]
                {
                        "###", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetChestplate), new Object[]
                {
                        "# #", "###", "###", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetLeggings), new Object[]
                {
                        "###", "# #", "# #", '#', "ingotMagnet"
                });
        registerShapedOreRecipe(new ItemStack(ItemInitializer.magnetBoots), new Object[]
                {
                        "# #", "# #", '#', "ingotMagnet"
                });
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
}
