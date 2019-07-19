package io.github.dawncraft.recipe;

import io.github.dawncraft.item.ItemInit;
import net.minecraft.item.ItemStack;

/**
 * Register fishing.
 *
 * @author QingChenW
 */
public class FishingLoader
{
    public static void initFishing()
    {
	registerFish(FishableCategory.FISH, new ItemStack(ItemInit.frog), 10);
    }

    private static void registerFish(FishableCategory category, ItemStack itemstack, int weight)
    {
	registerFish(category, new WeightedRandomFishable(itemstack, weight));
    }

    private static void registerFish(FishableCategory category, WeightedRandomFishable item)
    {
	switch(category)
	{
	case JUNK: FishingHooks.addJunk(item); break;
	case FISH: FishingHooks.addFish(item); break;
	case TREASURE: FishingHooks.addTreasure(item); break;
	}
    }
}
