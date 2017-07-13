package io.github.dawncraft.enchantment;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.config.ConfigLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFireBurn extends Enchantment
{
    public EnchantmentFireBurn()
    {
        super(ConfigLoader.enchantmentFireBurnId, new ResourceLocation(dawncraft.MODID + ":" + "fire_burn"), 1,
                EnumEnchantmentType.DIGGER);
        this.setName("fireBurn");
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
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
