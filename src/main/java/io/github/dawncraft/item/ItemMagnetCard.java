package io.github.dawncraft.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
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
    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();

            if(!nbt.getBoolean("hasWrited"))
            {
                nbt.setBoolean("hasWrited", true);
                nbt.setString("Owner", playerIn.getName());
                nbt.setString("UUID", playerIn.getUniqueID().toString());
            }
        }
        else
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean("hasWrited", true);
            nbt.setString("Owner", playerIn.getName());
            nbt.setString("UUID", playerIn.getUniqueID().toString());
            stack.setTagCompound(nbt);
        }
        // TODO 磁卡需完善
        
        return stack;
    }
    
    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt)
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            boolean flag = nbt.getBoolean("hasWrited");
            if(flag)
            {
                String name = nbt.getString("Owner");
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.magnetCard.tooltip", name));
            }
            else
            {
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.magnetCard.tooltip", "无"));
            }
        }
    }
}
