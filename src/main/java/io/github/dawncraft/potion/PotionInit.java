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
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Register some potions.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
@ObjectHolder(Dawncraft.MODID)
public class PotionInit
{
    public static final Potion RECOVER = null;
    public static final Potion SILENT = null;
    public static final Potion PARALYSIS = null;
    public static final Potion CONFUSION = null;

    public static final Potion BRAIN_DEAD = null;
    public static final Potion GER_POWER = null;
    public static final Potion BAD_GER = null;

    public static final PotionType RECOVERY = null;
    public static final PotionType LONG_RECOVERY = null;
    public static final PotionType STRONG_RECOVERY = null;

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
                    entityMana = entity.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
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
        registerPotionType(new PotionType(new PotionEffect(RECOVER, 900)), "recovery");
        registerPotionType(new PotionType("recover", new PotionEffect(RECOVER, 1800)), "long_recovery");
        registerPotionType(new PotionType("recover", new PotionEffect(RECOVER, 450, 1)), "strong_recovery");
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
