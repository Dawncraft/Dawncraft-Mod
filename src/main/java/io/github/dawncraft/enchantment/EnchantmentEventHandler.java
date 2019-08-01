package io.github.dawncraft.enchantment;

import io.github.dawncraft.Dawncraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class EnchantmentEventHandler
{
    @SubscribeEvent
    public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (!event.getWorld().isRemote && event.getHarvester() != null)
        {
            ItemStack itemStack = event.getHarvester().getActiveItemStack();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FIRE_BURN, itemStack) > 0
                    && itemStack.getItem() != Items.SHEARS)
            {
                for (int i = 0; i < event.getDrops().size(); ++i)
                {
                    ItemStack stack = event.getDrops().get(i);
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null)
                    {
                        newStack = newStack.copy();
                        newStack.setCount(stack.getCount());
                        event.getDrops().set(i, newStack);
                    }
                }
            }
        }
    }
}
