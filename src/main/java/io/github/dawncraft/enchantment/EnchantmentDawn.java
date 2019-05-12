package io.github.dawncraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDawn extends Enchantment
{
    protected EnchantmentDawn(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType)
    {
        super(enchID, enchName, enchWeight, enchType);
    }
}
