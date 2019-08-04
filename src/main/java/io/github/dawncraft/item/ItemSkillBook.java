package io.github.dawncraft.item;

import java.util.List;

import javax.annotation.Nullable;

import io.github.dawncraft.capability.CapabilityInit;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && stack.hasTagCompound())
        {
            SkillStack skillStack = new SkillStack(stack.getTagCompound().getCompoundTag("Skill"));
            if (skillStack.getSkill() != null)
            {
                SkillInventoryPlayer inventory = player.getCapability(CapabilityInit.PLAYER_MAGIC, null).getSkillInventory();
                if (inventory.addSkillStackToInventory(skillStack))
                {
                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    if (!player.capabilities.isCreativeMode)
                    {
                        stack.shrink(1);
                    }
                    player.addStat(StatList.getObjectUseStats(this));
                    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Skill", NBT.TAG_COMPOUND))
        {
            SkillStack skillStack = new SkillStack(stack.getTagCompound().getCompoundTag("Skill"));
            if (skillStack.getSkill() != null)
            {
                tooltip.add(TextFormatting.GRAY + I18n.format("item.skillBook.desc", skillStack.getDisplayName()));
            }
        }
        else
        {
            tooltip.add(TextFormatting.GRAY + I18n.format("item.skillBook.null"));
        }
    }
}
