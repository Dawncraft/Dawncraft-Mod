package io.github.dawncraft.enchantment;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ItemWand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentWandEnhancement extends Enchantment
{
    public EnchantmentWandEnhancement(int enchID, String enchName, int enchWeight)
    {
        super(enchID, new ResourceLocation(Dawncraft.MODID + ":" + enchName), enchWeight, EnchantmentLoader.WAND);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }
    
    @Override
    public boolean canApply(ItemStack stack)
    {
        return super.canApply(stack);
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return stack.getItem() instanceof ItemWand ? true : super.canApplyAtEnchantingTable(stack);
    }
}