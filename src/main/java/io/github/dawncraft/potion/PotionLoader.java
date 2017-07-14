package io.github.dawncraft.potion;

import io.github.dawncraft.dawncraft;
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
        potionGerPower = new PotionBase(new ResourceLocation(dawncraft.MODID + ":" + "ger_power"), true, 0x7F0000).setPotionName("potion.gerPower");
        potionBadGer = new PotionBase(new ResourceLocation(dawncraft.MODID + ":" + "bad_ger"), true, 0x7F0000).setPotionName("potion.badGer");
        potionBrainDead = new PotionBase(new ResourceLocation(dawncraft.MODID + ":" + "brain_dead"), true, 0x7F0000).setPotionName("potion.brainDead");
    }
}
