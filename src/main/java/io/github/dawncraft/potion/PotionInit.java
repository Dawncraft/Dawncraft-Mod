package io.github.dawncraft.potion;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityInit;
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
    public static Potion RECOVER;
    public static Potion SILENT;
    public static Potion PARALYSIS;
    public static Potion CONFUSION;

    public static Potion BRAIN_DEAD;
    public static Potion GER_POWER;
    public static Potion BAD_GER;

    public static PotionType RECOVERY;
    public static PotionType LONG_RECOVERY;
    public static PotionType STRONG_RECOVERY;

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        RECOVER = registerPotion(new PotionBase(false, 0x0000FF)
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
                    entityMana = entity.getCapability(CapabilityInit.PLAYER_MAGIC, null);
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
        SILENT = registerPotion(new PotionBase(true, 0x585858).setPotionName("potion.silent"), "silent");
        PARALYSIS = registerPotion(new PotionBase(true, 0x3C64C8).setPotionName("potion.paralysis"), "paralysis");
        CONFUSION = registerPotion(new PotionBase(true, 0x649664).setPotionName("potion.confusion"), "confusion");
        BRAIN_DEAD = registerPotion(new PotionBase(true, 0x7F0000).setPotionName("potion.brainDead"), "brain_dead");
        GER_POWER = registerPotion(new PotionBase(false, 0x7F0000).setPotionName("potion.gerPower"), "ger_power");
        BAD_GER = registerPotion(new PotionBase(true, 0x7F0000).setPotionName("potion.badGer"), "bad_ger");
    }

    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event)
    {
        RECOVERY = registerPotionType(new PotionType(new PotionEffect(RECOVER, 900)), "recovery");
        LONG_RECOVERY = registerPotionType(new PotionType("recover", new PotionEffect(RECOVER, 1800)), "long_recovery");
        STRONG_RECOVERY = registerPotionType(new PotionType("recover", new PotionEffect(RECOVER, 450, 1)), "strong_recovery");
    }

    /**
     * Register a potion with a string id.
     *
     * @param potion The potion to register
     * @param name The potion's string id
     * @return
     */
    private static Potion registerPotion(Potion potion, String name)
    {
        ForgeRegistries.POTIONS.register(potion.setRegistryName(name));
        return potion;
    }

    /**
     * Register a potion type with a string id.
     *
     * @param potion The potion type to register
     * @param name The potion type's string id
     * @return
     */
    private static PotionType registerPotionType(PotionType potionType, String name)
    {
        ForgeRegistries.POTION_TYPES.register(potionType.setRegistryName(name));
        return potionType;
    }
}
