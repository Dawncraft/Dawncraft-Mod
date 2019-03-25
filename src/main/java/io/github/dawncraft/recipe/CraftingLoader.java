package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
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
