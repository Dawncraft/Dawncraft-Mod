package com.github.wdawning.dawncraft.enchantment;

import net.minecraft.enchantment.Enchantment;

import com.github.wdawning.dawncraft.common.ConfigLoader;

public class EnchantmentLoader
{
    public static Enchantment fireBurn;

    public EnchantmentLoader()
    {
        try
        {
            fireBurn = new EnchantmentFireBurn();
            Enchantment.addToBookList(fireBurn);
        }
        catch (Exception e)
        {
            ConfigLoader.logger().error("Duplicate or illegal enchantment id: {}, the registry of class '{}' will be skipped. ",
                    ConfigLoader.enchantmentFireBurnId, EnchantmentFireBurn.class.getName());
        }
    }
}
