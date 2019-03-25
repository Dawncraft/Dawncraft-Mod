package io.github.dawncraft.item;

import java.util.List;
import java.util.UUID;

import io.github.dawncraft.stats.AchievementLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO 磁卡和磁铁门未经测试
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
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            if (itemStack.hasTagCompound())
            {
                NBTTagCompound nbt = itemStack.getTagCompound();
                if (nbt.hasKey("Owner", 8))
                {
                    MinecraftServer server = MinecraftServer.getServer();
                    EntityPlayerMP ep = server.getConfigurationManager().getPlayerByUsername(nbt.getString("Owner"));
                    if (nbt.hasKey("UUID", 8))
                    {
                        String oldUuid = nbt.getString("UUID");
                        String newUuid = EntityPlayer.getUUID(ep.getGameProfile()).toString();
                        if (!oldUuid.equals(newUuid))
                        {
                            ep = server.getConfigurationManager().getPlayerByUUID(UUID.fromString(oldUuid));
                            nbt.setString("Owner", ep.getName());
                        }
                    }
                    else
                    {
                        nbt.setString("UUID", EntityPlayer.getUUID(ep.getGameProfile()).toString());
                    }
                }
            }
            else
            {
                ItemStack newItemStack = new ItemStack(ItemLoader.magnetCard);

                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("Owner", player.getName());
                nbt.setString("UUID", player.getUniqueID().toString());
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
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            String name = nbt.getString("Owner");
            if (!StringUtils.isNullOrEmpty(name))
            {
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.magnetCard.desc", name));
            }
        }
        else
        {
            tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.magnetCard.null"));
        }
    }
}
