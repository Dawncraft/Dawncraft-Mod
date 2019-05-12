package io.github.dawncraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.network.MessageWindowSkills;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.SkillStack;

public class ItemSkillBook extends Item
{
    public ItemSkillBook()
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
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote && itemStack.hasTagCompound())
        {
            SkillStack skillStack = SkillStack.loadSkillStackFromNBT(itemStack.getTagCompound());
            if (skillStack != null)
            {
                SkillInventoryPlayer inventory = player.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
                if (inventory.addSkillStackToInventory(skillStack))
                {
                    world.playSoundAtEntity(player, "random.pop", 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    List<SkillStack> list = new ArrayList<SkillStack>();
                    for(int i = 0; i < inventory.getInventorySize(); i++)
                        list.add(inventory.getStackInSlot(i));
                    NetworkLoader.instance.sendTo(new MessageWindowSkills(0, list), (EntityPlayerMP) player);
                }
                --itemStack.stackSize;
            }
        }
        return itemStack;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            SkillStack skillStack = SkillStack.loadSkillStackFromNBT(stack.getTagCompound());
            if (skillStack != null)
            {
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.skillBook.desc", skillStack.getDisplayName()));
            }
        }
        else
        {
            tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.skillBook.null"));
        }
    }
}