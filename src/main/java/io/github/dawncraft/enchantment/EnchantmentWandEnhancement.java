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
    public int getMinEnchantability(int level)
    {
        return 1 + (level - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int level)
    {
        return this.getMinEnchantability(level) + 20;
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