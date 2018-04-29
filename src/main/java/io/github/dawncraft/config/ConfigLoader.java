package io.github.dawncraft.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register a configuration manager and load configurations.
 *
 * @author QingChenW
 */
public class ConfigLoader
{
    private static Configuration config;

    @ConfigItem(EnumCategories.ENERGY)
    public static boolean isEnergyEnabled = true;

    @ConfigItem(EnumCategories.MAGNET)
    public static boolean isMagnetEnabled = true;

    @ConfigItem(EnumCategories.MACHINE)
    public static boolean isMachineEnabled = true;

    @ConfigItem(EnumCategories.COMPUTER)
    public static boolean isComputerEnabled = true;

    @ConfigItem(EnumCategories.SCIENCE)
    public static boolean isScienceEnabled = true;

    @ConfigItem(EnumCategories.FURNITURE)
    public static boolean isFurnitureEnabled = true;
    @ConfigItem(EnumCategories.FURNITURE)
    public static int chairHealAmount = 0;

    @ConfigItem(EnumCategories.CUISINE)
    public static boolean isCuisineEnabled = true;

    @ConfigItem(EnumCategories.WEAPON)
    public static boolean isWeaponEnabled = true;

    @ConfigItem(EnumCategories.MAGIC)
    public static boolean isMagicEnabled = true;
    @ConfigItem(EnumCategories.MAGIC)
    public static boolean manaRenderType = true;
    @ConfigItem(EnumCategories.MAGIC)
    public static int publicPrepareTicks = 20;
    @ConfigItem(EnumCategories.MAGIC)
    public static int publicCooldownTicks = 20;

    public static boolean isColoreggEnabled()
    {
        return gerKingPassword.equals(String.valueOf(0x459b7d));
    }
    @ConfigItem(EnumCategories.COLOREGG)
    public static String gerKingPassword = "Please guess password!";
    @ConfigItem(EnumCategories.COLOREGG)
    public static int enchantmentFireBurnId = 36;
    
    public ConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        this.reload();
    }
    
    public static void reload()
    {
        LogLoader.logger().info("Started loading configuration.");
        
        loadConfig(config(), ConfigLoader.class);
        
        LogLoader.logger().info("Finished loading configuration.");
    }
    
    public static Configuration config()
    {
        return config;
    }
    
    /**
     * Load your configuration and inject value into class' fields automatically.
     *
     * @param config The configuration to load
     * @param clazz A class which has some static fields annotated by {@link ConfigItem}
     */
    public static void loadConfig(Configuration config, Class clazz)
    {
        ModContainer container = Loader.instance().activeModContainer();
        String modid = container != null ? container.getModId().toLowerCase() : "minecraft";
        
        for(Field field : clazz.getDeclaredFields())
        {
            if(field.isAnnotationPresent(ConfigItem.class))
            {
                ConfigItem item = field.getAnnotation(ConfigItem.class);
                try
                {
                    field.setAccessible(true);

                    String name = field.getName();
                    String category = item.value().getName();
                    String langKey = "config." + modid + "." + category + "." + name;
                    String comment = StatCollector.translateToLocal(langKey + ".tooltip");
                    Object defValue = field.get(null);
                    Object value = defValue;

                    if (defValue instanceof Boolean)
                    {
                        value = config.get(category, name, (Boolean) defValue, comment).setLanguageKey(langKey).getBoolean();
                    }
                    else if (defValue instanceof boolean[])
                    {
                        value = config.get(category, name, (boolean[]) defValue, comment).setLanguageKey(langKey).getBooleanList();
                    }
                    else if (defValue instanceof Integer)
                    {
                        value = config.get(category, name, (Integer) defValue, comment).setLanguageKey(langKey).getInt();
                    }
                    else if (defValue instanceof int[])
                    {
                        value = config.get(category, name, (int[]) defValue, comment).setLanguageKey(langKey).getIntList();
                    }
                    else if (defValue instanceof Double)
                    {
                        value = config.get(category, name, (Double) defValue, comment).setLanguageKey(langKey).getDouble();
                    }
                    else if (defValue instanceof double[])
                    {
                        value = config.get(category, name, (double[]) defValue, comment).setLanguageKey(langKey).getDoubleList();
                    }
                    else if (defValue instanceof String)
                    {
                        value = config.get(category, name, (String) defValue, comment).setLanguageKey(langKey).getString();
                    }
                    else if (defValue instanceof String[])
                    {
                        value = config.get(category, name, (String[]) defValue, comment).setLanguageKey(langKey).getString();
                    }

                    field.set(null, value);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        for (String category : config.getCategoryNames())
        {
            String langKey = "config." + modid + "." + category;
            config.setCategoryLanguageKey(category, langKey);
            config.setCategoryComment(category, StatCollector.translateToLocal(langKey + ".tooltip"));
        }
        
        config.save();
    }
    
    /**
     * An Annotation for injecting value into field automatically.
     *
     * @author QingChenW
     */
    @Retention(RUNTIME)
    @Target(FIELD)
    public @interface ConfigItem
    {
        /**
         * @return The config's category
         */
        EnumCategories value() default EnumCategories.DEFAULT;
    }
}
