package io.github.dawncraft.enchantment;

import io.github.dawncraft.api.item.ItemWand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentWandEnhancement extends Enchantment
{
    public EnchantmentWandEnhancement()
    {
        super(Rarity.COMMON, EnchantmentInit.WAND, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemWand ? true : super.canApply(stack);
    }
}