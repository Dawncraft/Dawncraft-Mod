package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Register smelting and fuels.
 *
 * @author QingChenW
 */
public class SmeltingLoader
{
    public static void initSmelting()
    {
	registerSmelting(BlockInit.magnetOre, new ItemStack(ItemInit.magnetIngot), 0.7F);
	registerSmelting(Items.EGG, new ItemStack(ItemInit.cookedEgg), 0.3F);
	registerFuel(ItemInit.bucketPetroleum, 25600);
    }

    private static void registerSmelting(Item input, ItemStack output, float xp)
    {
	GameRegistry.addSmelting(input, output, xp);
    }

    private static void registerSmelting(Block input, ItemStack output, float xp)
    {
	GameRegistry.addSmelting(input, output, xp);
    }

    @Deprecated
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

    @Deprecated
    private static void registerFuel(IFuelHandler handler)
    {
	GameRegistry.registerFuelHandler(handler);
    }
}
