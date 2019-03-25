package io.github.dawncraft.enchantment;

import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.LogLoader;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Register enchantments.
 *
 * @author QingChenW
 */
public class EnchantmentLoader
{
    // Enchantment type
    public static final EnumEnchantmentType WAND = EnumHelper.addEnchantmentType("WAND");

    // Magic
    public static Enchantment enhancement = new EnchantmentWandEnhancement(ConfigLoader.enchantmentEnhancementId, "enhancement", 10).setName("enhancement");
    // ColorEgg
    public static Enchantment fireBurn = new EnchantmentFireBurn(ConfigLoader.enchantmentFireBurnId, "fire_burn", 1).setName("fireBurn");

    public static void initEnchantments()
    {
        registerEnchantment(enhancement);
        registerEnchantment(fireBurn);
    }
    
    private static void registerEnchantment(Enchantment enchantment)
    {
        try
        {
            Enchantment.addToBookList(enchantment);
        }
        catch (Exception e)
        {
            LogLoader.logger().error("Duplicate or illegal enchantment id: {}, the registry of class '{}' will be skipped. ",
                    enchantment.effectId, enchantment.getName());
        }
    }
}
