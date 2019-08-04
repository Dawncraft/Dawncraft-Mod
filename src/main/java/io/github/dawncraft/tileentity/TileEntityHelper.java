package io.github.dawncraft.tileentity;

import java.util.HashSet;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntityHelper
{
    public static IItemHandler[] getInventoriesFromTileEntity(@Nullable TileEntity te)
    {
        if (te == null)
        {
            return null;
        }
        else
        {
            HashSet<IItemHandler> invs = Sets.newHashSet();

            if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
                invs.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
            for (EnumFacing facing : EnumFacing.VALUES)
            {
                if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing))
                    invs.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing));
            }

            return invs.toArray(new IItemHandler[0]);
        }
    }

    public static void dropInventoriesItems(World world, BlockPos pos, IItemHandler... invs)
    {
        for (IItemHandler inv : invs)
        {
            for (int i = inv.getSlots() - 1; i >= 0; --i)
            {
                if (inv.getStackInSlot(i) != ItemStack.EMPTY)
                {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inv.getStackInSlot(i));
                    ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public static int calcRedstone(@Nullable TileEntity te)
    {
        return calcRedstoneFromInventories(getInventoriesFromTileEntity(te));
    }

    /**
     * See {@link ItemHandlerHelper#calcRedstoneFromInventory(IItemHandler)}
     */
    public static int calcRedstoneFromInventories(@Nullable IItemHandler... invs)
    {
        if (invs == null || invs.length == 0)
        {
            return 0;
        }
        else
        {
            int itemsFound = 0;
            int slots = 0;
            float proportion = 0.0F;

            for (IItemHandler inv : invs)
            {
                for (int j = 0; j < inv.getSlots(); ++j)
                {
                    ItemStack itemStack = inv.getStackInSlot(j);

                    if (!itemStack.isEmpty())
                    {
                        proportion += itemStack.getCount() / (float) Math.min(inv.getSlotLimit(j), itemStack.getMaxStackSize());
                        ++itemsFound;
                    }
                }
                slots += inv.getSlots();
            }

            proportion = proportion / slots;
            return MathHelper.floor(proportion * 14.0F) + (itemsFound > 0 ? 1 : 0);
        }
    }
}
