package io.github.dawncraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemInitializer;

/**
 * Register smelting and fuels.
 *
 * @author QingChenW
 */
public class SmeltingLoader
{
    public static void initSmelting()
    {
        registerSmelting(BlockLoader.magnetOre, new ItemStack(ItemInitializer.magnetIngot), 0.7F);
        registerSmelting(Items.egg, new ItemStack(ItemInitializer.cookedEgg), 0.3F);
        registerFuel(ItemInitializer.bucketPetroleum, 25600);
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
}
