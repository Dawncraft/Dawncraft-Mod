package io.github.dawncraft.enchantment;

import io.github.dawncraft.common.ConfigLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register enchantments.
 * 
 * @author QingChenW
 */
public class EnchantmentLoader
{
    public static Enchantment fireBurn;
    
    public EnchantmentLoader(FMLPreInitializationEvent event)
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
