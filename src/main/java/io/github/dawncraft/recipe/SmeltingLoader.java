package io.github.dawncraft.recipe;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.fluid.FluidInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Register some smelt recipes and fuels.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class SmeltingLoader
{
    public static void initSmelting()
    {
        registerSmelting(BlockInit.MAGIC_ORE, new ItemStack(ItemInit.MAGNET_INGOT), 0.7F);
        registerSmelting(Items.EGG, new ItemStack(ItemInit.COOKED_EGG), 0.3F);
    }

    @SubscribeEvent
    public static void onGetFuelBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        if (event.getItemStack().getItem() instanceof UniversalBucket)
        {
            FluidStack stack = FluidUtil.getFluidContained(event.getItemStack());
            if (stack.getFluid() == FluidInit.PETROLEUM)
            {
                event.setBurnTime(25600);
            }
        }
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
                return fuel.getItem() == input ? burnTime : 0;
            }
        });
    }

    @Deprecated
    private static void registerFuel(IFuelHandler handler)
    {
        GameRegistry.registerFuelHandler(handler);
    }
}
