package com.github.wdawning.dawncraft.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import com.github.wdawning.dawncraft.common.ConfigLoader;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PotionLoader
{
	public static Potion potionGerPower;
	public static Potion potionBadGer;
	public static Potion potionBrainDead;

    public PotionLoader(FMLPreInitializationEvent event)
    {
        if (PotionLoader.expandPotionTypes())
        {
            int i = ConfigLoader.potionGerPowerID;
            if (i > 0 && i < 256 && Potion.potionTypes[i] == null)
            {
            	potionGerPower = new PotionGerPower();
            	potionBadGer = new PotionBadGer();
            	potionBrainDead = new PotionBrainDead();
            }
            else
            {
                event.getModLog().error("Duplicate or illegal potion id: {}, the registry of class '{}' will be skipped. ", i, PotionGerPower.class.getName());
            }
        }
        else
        {
            event.getModLog().error("Find error while resizing potionTypes, the registry of potions will be skipped. ");
        }
        if (!PotionLoader.expandPotionTypes())
        {
            event.getModLog().error("Find error while resizing potionTypes, the registry of potions will be skipped. ");
        }
    }

    /**
     * @return true if it succeeded
     */
    public static boolean expandPotionTypes()
    {
        for (Field field : Potion.class.getDeclaredFields())
        {
            field.setAccessible(true);
            if (Potion.class.equals(field.getType().getComponentType()))
            {
                try
                {
                    Field fieldmodifiers = Field.class.getDeclaredField("modifiers");
                    fieldmodifiers.setAccessible(true);
                    fieldmodifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    Potion[] potionTypes = (Potion[]) field.get(null);
                    field.set(null, Arrays.copyOf(potionTypes, 256));
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        return false;
    }
}