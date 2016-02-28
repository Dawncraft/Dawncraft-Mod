package WdawningStudio.DawnW.science.crafting;

import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
        GameRegistry.addRecipe(new ItemStack(BlockLoader.magnet_block), new Object[]
        {
                "###", "###", "###", '#', ItemLoader.magnet_ingot
        });
        GameRegistry.addShapelessRecipe(new ItemStack(ItemLoader.magnet_ingot, 9), BlockLoader.magnet_block);
    }

    private static void registerSmelting()
    {
        GameRegistry.addSmelting(BlockLoader.magnet_ore, new ItemStack(ItemLoader.magnet_ingot), 0.7F);
    }

    private static void registerFuel()
    {
    	/**
        GameRegistry.registerFuelHandler(new IFuelHandler()
        {
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                return Items.diamond != fuel.getItem() ? 0 : 12800;
            }
        });
        **/
    }
}