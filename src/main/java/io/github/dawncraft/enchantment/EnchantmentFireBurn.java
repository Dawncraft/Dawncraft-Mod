package io.github.dawncraft.enchantment;

import io.github.dawncraft.Dawncraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFireBurn extends EnchantmentDawn
{
    public EnchantmentFireBurn(int enchID, String enchName, int enchWeight)
    {
        super(enchID, new ResourceLocation(Dawncraft.MODID + ":" + enchName), enchWeight, EnumEnchantmentType.DIGGER);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench.effectId != silkTouch.effectId && ench.effectId != fortune.effectId;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() == Items.shears ? true : super.canApply(stack);
    }
}
