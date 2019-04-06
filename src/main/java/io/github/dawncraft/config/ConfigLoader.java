package io.github.dawncraft.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import io.github.dawncraft.ModCategories;

import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

/**
 * Register a configuration manager and load configurations.
 *
 * @author QingChenW
 */
public class ConfigLoader
{
    private static Configuration config;
    
    @ConfigItem(ModCategories.ENERGY)
    public static boolean isEnergyEnabled = true;
    
    @ConfigItem(ModCategories.MAGNET)
    public static boolean isMagnetEnabled = true;
    
    @ConfigItem(ModCategories.MACHINE)
    public static boolean isMachineEnabled = true;
    
    @ConfigItem(ModCategories.COMPUTER)
    public static boolean isComputerEnabled = true;
    
    @ConfigItem(ModCategories.SCIENCE)
    public static boolean isScienceEnabled = true;
    
    @ConfigItem(ModCategories.FURNITURE)
    public static boolean isFurnitureEnabled = true;
    @ConfigItem(ModCategories.FURNITURE)
    public static int chairHealAmount = 0;
    
    @ConfigItem(ModCategories.CUISINE)
    public static boolean isCuisineEnabled = true;
    @ConfigItem(ModCategories.CUISINE)
    public static boolean isThirstEnabled = false;
    
    @ConfigItem(ModCategories.WAR)
    public static boolean isWarEnabled = true;
    
    @ConfigItem(ModCategories.MAGIC)
    public static boolean isMagicEnabled = true;
    @ConfigItem(ModCategories.MAGIC)
    public static boolean manaRenderType = true;
    @ConfigItem(ModCategories.MAGIC)
    public static int globalPrepareTicks = 20;
    @ConfigItem(ModCategories.MAGIC)
    public static int globalCooldownTick = 20;
    @ConfigItem(ModCategories.MAGIC)
    public static int enchantmentEnhancementId = 52;
    public static boolean isColoreggEnabled()
    {
        return gerKingPassword.hashCode() == 474280700;
    }
    @ConfigItem(ModCategories.COLOREGG)
    public static String gerKingPassword = "Please guess password!";
    @ConfigItem(ModCategories.COLOREGG)
    public static double rangeToCheck = 32.0F;
    @ConfigItem(ModCategories.COLOREGG)
    public static int enchantmentFireBurnId = 36;

    public static void init(File file)
    {
        config = new Configuration(file);
        config.load();
        reload();
    }

    public static void reload()
    {
        LogLoader.logger().info("Started loading configuration.");

        loadConfig(config, ConfigLoader.class);

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
                    
                    // TODO 修复配置读取
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
                    else if (defValue instanceof Float)
                    {
                        LogLoader.logger().error("Our config system don't suppot float now.");
                    }
                    else if (defValue instanceof float[])
                    {
                        LogLoader.logger().error("Our config system don't suppot float array now.");
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
        ModCategories value() default ModCategories.DEFAULT;
    }
}
