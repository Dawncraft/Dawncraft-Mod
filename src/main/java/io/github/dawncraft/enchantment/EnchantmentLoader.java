package io.github.dawncraft.enchantment;

import io.github.dawncraft.config.LogLoader;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register enchantments.
 *
 * @author QingChenW
 */
public class EnchantmentLoader
{
    public static Enchantment fireBurn = new EnchantmentFireBurn().setName("fireBurn");

    public EnchantmentLoader(FMLPreInitializationEvent event)
    {
        register(fireBurn);
    }
    
    private static void register(Enchantment enchantment)
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
