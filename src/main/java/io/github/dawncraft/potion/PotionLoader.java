package io.github.dawncraft.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register potions.
 * 
 * @author QingChenW
 */
public class PotionLoader
{
    public static Potion potionGerPower;
    public static Potion potionBadGer;
    public static Potion potionBrainDead;
    
    public PotionLoader(FMLPreInitializationEvent event)
    {
        potionGerPower = new PotionGerPower();
        potionBadGer = new PotionBadGer();
        potionBrainDead = new PotionBrainDead();
    }
}
