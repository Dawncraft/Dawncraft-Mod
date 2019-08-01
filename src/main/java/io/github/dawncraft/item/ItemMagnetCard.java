package io.github.dawncraft.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetCard extends Item
{
    public ItemMagnetCard()
    {
        super();
        this.setMaxStackSize(16);
    }

    @Override
    public int getItemStackLimit(ItemStack itemStack)
    {
        return itemStack.hasTagCompound() ? 1 : super.getItemStackLimit(itemStack);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote)
        {
            if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("UUID", 8))
            {
                ItemStack newStack = new ItemStack(ItemInit.MAGNET_CARD);

                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("UUID", UUID.randomUUID().toString());
                nbt.setString("Owner", player.getName());
                nbt.setString("Text", I18n.format(this.getTranslationKey() + ".owner", player.getName()));
                newStack.setTagCompound(nbt);

                stack.shrink(1);
                if (stack.getCount() <= 0)
                    return new ActionResult<>(EnumActionResult.SUCCESS, newStack);
                if (!player.inventory.addItemStackToInventory(newStack.copy()))
                    player.dropItem(newStack, false);

                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            String uuid = nbt.getString("UUID");
            if (!StringUtils.isNullOrEmpty(uuid))
            {
                tooltip.add(TextFormatting.GRAY + I18n.format(this.getTranslationKey() + ".id", uuid));
                String text = nbt.getString("Text");
                if (!StringUtils.isNullOrEmpty(text))
                {
                    tooltip.add(I18n.format(this.getTranslationKey() + ".desc", text));
                }
            }
        }
        else
        {
            tooltip.add(TextFormatting.GRAY + I18n.format(this.getTranslationKey() + ".null"));
        }
    }
}
