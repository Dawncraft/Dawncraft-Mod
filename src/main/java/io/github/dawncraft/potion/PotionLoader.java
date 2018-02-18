package io.github.dawncraft.potion;

import java.lang.reflect.Field;
import java.util.Map;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.entity.immortal.EntityImmortal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Register potions.
 *
 * @author QingChenW
 */
public class PotionLoader
{
    public static final ResourceLocation POTION_TEXTURE = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/potion.png");
    
    public static Potion potionRecover = new PotionBase("recover", false, 0x0000FF)
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
            if(entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                if(player.hasCapability(CapabilityLoader.magic, null))
                {
                    IMagic magic = player.getCapability(CapabilityLoader.magic, null);

                    if(magic.getMana() < magic.getMaxMana())
                    {
                        magic.recover(1.0F);
                    }
                }
            }
            else if(entity instanceof EntityImmortal)
            {
                EntityImmortal god = (EntityImmortal) entity;
                
                if (god.getMana() < god.getMaxMana())
                {
                    god.recover(1.0F);
                }
            }
        };
    }.setPotionName("potion.recover");
    public static Potion potionSilent = new PotionBase("silent", true, 0x585858).setPotionName("potion.silent");
    public static Potion potionParalysis = new PotionBase("paralysis", true, 0x3C64C8).setPotionName("potion.paralysis");
    public static Potion potionConfusion = new PotionBase("confusion", true, 0x649664).setPotionName("potion.confusion");

    public static Potion potionBrainDead = new PotionBase("brain_dead", true, 0x7F0000).setPotionName("potion.brainDead");
    public static Potion potionGerPower = new PotionBase("ger_power", false, 0x7F0000).setPotionName("potion.gerPower");
    public static Potion potionBadGer = new PotionBase("bad_ger", true, 0x7F0000).setPotionName("potion.badGer");
    
    public PotionLoader(FMLPreInitializationEvent event)
    {
        register(potionGerPower, "0 & !1 & !2 & !3 & 0+6", "5");
    }
    
    private static void register(Potion potion, String recipe, String amplifier)
    {
        reflectPotionHelper(potion, recipe, amplifier);
    }

    /**
     * 公共API,注册你的药水物品吧
     * <br>Mojang写的都是啥啊,自己看吧 {@link net.minecraft.potion.PotionHelper}</br>
     *
     * @param potion 要注册的药水
     * @param recipe 药水酿造方式
     * @param amplifier 理论上是等级可是...
     *
     * @author QingChenW
     */
    @Deprecated
    public static void reflectPotionHelper(Potion potion, String recipe, String amplifier)
    {
        try
        {
            Field field = ReflectionHelper.findField(PotionHelper.class, "potionRequirements", "field_179539_o");
            Map<Integer, String> potionRequirements = (Map<Integer, String>) field.get(null);
            potionRequirements.put(potion.getId(), recipe);
            EnumHelper.setFailsafeFieldValue(field, null, potionRequirements);
            
            Field field2 = ReflectionHelper.findField(PotionHelper.class, "potionAmplifiers", "field_179540_p");
            Map<Integer, String> potionAmplifiers = (Map<Integer, String>) field2.get(null);
            potionAmplifiers.put(potion.getId(), amplifier);
            EnumHelper.setFailsafeFieldValue(field2, null, potionAmplifiers);
        }
        catch (Exception e)
        {
            LogLoader.logger().error("Reflect potion helper failed: {}", e.toString());
        }
    }
}
