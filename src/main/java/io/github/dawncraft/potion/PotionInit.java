package io.github.dawncraft.potion;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IEntityMana;
import io.github.dawncraft.entity.immortal.EntityImmortal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Register some potions.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class PotionInit
{
    public static Potion potionRecover;
    public static Potion potionSilent;
    public static Potion potionParalysis;
    public static Potion potionConfusion;

    public static Potion potionBrainDead;
    public static Potion potionGerPower;
    public static Potion potionBadGer;

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        registerPotion(new PotionBase(false, 0x0000FF)
        {
            @Override
            public boolean isReady(int duration, int amplifier)
            {
                int k = 50 >> amplifier;
                return k > 0 ? duration % k == 0 : true;
            };

            @Override
            public void performEffect(EntityLivingBase entity, int amplifier)
            {
                IEntityMana entityMana = null;

                if (entity instanceof EntityPlayer)
                {
                    entityMana = entity.getCapability(CapabilityLoader.playerMagic, null);
                }
                else if (entity instanceof EntityImmortal)
                {
                    entityMana = (EntityImmortal) entity;
                }

                if (entityMana != null && entityMana.shouldRecover())
                {
                    entityMana.recover(1.0F);
                }
            };
        }.setPotionName("potion.recover"), "recover");
        registerPotion(new PotionBase(true, 0x585858).setPotionName("potion.silent"), "silent");
        registerPotion(new PotionBase(true, 0x3C64C8).setPotionName("potion.paralysis"), "paralysis");
        registerPotion(new PotionBase(true, 0x649664).setPotionName("potion.confusion"), "confusion");
        registerPotion(new PotionBase(true, 0x7F0000).setPotionName("potion.brainDead"), "brain_dead");
        registerPotion(new PotionBase(false, 0x7F0000).setPotionName("potion.gerPower"), "ger_power");
        registerPotion(new PotionBase(true, 0x7F0000).setPotionName("potion.badGer"), "bad_ger");
    }

    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event)
    {
        registerPotionType(new PotionType(new PotionEffect(potionRecover, 900)), "regeneration");
        registerPotionType(new PotionType("recover", new PotionEffect(potionRecover, 1800)), "long_regeneration");
        registerPotionType(new PotionType("recover", new PotionEffect(potionRecover, 450, 1)), "strong_regeneration");
    }

    /**
     * Register a potion with a string id.
     *
     * @param potion The potion to register
     * @param name The potion's string id
     */
    private static void registerPotion(Potion potion, String name)
    {
        ForgeRegistries.POTIONS.register(potion.setRegistryName(name));
    }

    /**
     * Register a potion type with a string id.
     *
     * @param potion The potion type to register
     * @param name The potion type's string id
     */
    private static void registerPotionType(PotionType potionType, String name)
    {
        ForgeRegistries.POTION_TYPES.register(potionType.setRegistryName(name));
    }
}
