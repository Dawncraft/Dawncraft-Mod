package io.github.dawncraft.item;

import java.util.List;
import java.util.UUID;

import io.github.dawncraft.stats.AchievementLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetCard extends Item
{
    public ItemMagnetCard()
    {
        this.setMaxStackSize(16);
    }
    
    @Override
    public int getItemStackLimit(ItemStack itemStack)
    {
        return itemStack.hasTagCompound() ? 1 : super.getItemStackLimit(itemStack);
    }

    @Override
    public boolean doesSneakBypassUse(World world, BlockPos blockPos, EntityPlayer player)
    {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("UUID", 8))
            {
                ItemStack newItemStack = new ItemStack(ItemLoader.magnetCard);
                
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("UUID", UUID.randomUUID().toString());
                nbt.setString("Owner", player.getName());
                nbt.setString("Text", StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".owner", player.getName()));
                newItemStack.setTagCompound(nbt);

                --itemStack.stackSize;
                if (itemStack.stackSize <= 0) return newItemStack;
                if (!player.inventory.addItemStackToInventory(newItemStack.copy()))
                    player.dropPlayerItemWithRandomChoice(newItemStack, false);
            }
        }
        player.triggerAchievement(AchievementLoader.magnetCard);
        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (itemStack.hasTagCompound())
        {
            NBTTagCompound nbt = itemStack.getTagCompound();
            String uuid = nbt.getString("UUID");
            if (!StringUtils.isNullOrEmpty(uuid))
            {
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".id", uuid));
                String text = nbt.getString("Text");
                if (!StringUtils.isNullOrEmpty(text))
                {
                    tooltip.add(StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".desc", text));
                }
            }
        }
        else
        {
            tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".null"));
        }
    }
}
