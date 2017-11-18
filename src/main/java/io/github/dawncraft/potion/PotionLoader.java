package io.github.dawncraft.potion;

import java.lang.reflect.Field;
import java.util.Map;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.LogLoader;
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
    public static Potion potionParalysis = new PotionBase(new ResourceLocation(Dawncraft.MODID + ":" + "paralysis"), true, 0x7F0000).setPotionName("potion.paralysis");
    public static Potion potionConfusion = new PotionBase(new ResourceLocation(Dawncraft.MODID + ":" + "confusion"), true, 0x7F0000).setPotionName("potion.confusion");
    public static Potion potionBrainDead = new PotionBase(new ResourceLocation(Dawncraft.MODID + ":" + "brain_dead"), true, 0x7F0000).setPotionName("potion.brainDead");
    public static Potion potionGerPower = new PotionBase(new ResourceLocation(Dawncraft.MODID + ":" + "ger_power"), true, 0x7F0000).setPotionName("potion.gerPower");
    public static Potion potionBadGer = new PotionBase(new ResourceLocation(Dawncraft.MODID + ":" + "bad_ger"), true, 0x7F0000).setPotionName("potion.badGer");
    
    public PotionLoader(FMLPreInitializationEvent event)
    {
        register(potionGerPower, "", "");
    }
    
    private static void register(Potion potion, String recipe, String amplifier)
    {
        reflectPotionHelper(potion, amplifier, amplifier);
    }

    /**
     * 公共API,注册你的药水物品吧
     * <br>Mojang写的都是啥啊,自己看吧 {@link net.minecraft.potion.PotionHelper}</br>
     *
     * @param potion 要注册的药水
     * @param recipe 药水酿造方式
     * @param amplifier 未知
     *
     * @author QingChenW
     */
    public static void reflectPotionHelper(Potion potion, String recipe, String amplifier)
    {
        try
        {
            Field field = ReflectionHelper.findField(PotionHelper.class, "potionRequirements", "field_179539_o");
            Map<Integer, String> potionRecipes = (Map<Integer, String>) field.get(null);
            potionRecipes.put(potion.getId(), recipe);
            EnumHelper.setFailsafeFieldValue(field, null, potionRecipes);
        }
        catch (Exception e)
        {
            LogLoader.logger().error("Reflect potion helper failed: {}", e.toString());
        }
    }
}
