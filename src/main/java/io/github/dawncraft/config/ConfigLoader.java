package io.github.dawncraft.config;

import io.github.dawncraft.dawncraft;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register a configer and load configs.
 *
 * @author QingChenW
 */
public class ConfigLoader
{
    public static final String PASSWORD = "4561789";
    
    private static Configuration config;
    
    public static boolean isEnergyEnabled;
    
    public static boolean isMagnetismEnabled;
    
    public static boolean isMachineEnabled;
    
    public static boolean isComputerEnabled;
    
    public static boolean isScienceEnabled;
    
    public static boolean isFurnitureEnabled;
    
    public static boolean isFoodEnabled;
    
    public static boolean isFlanEnabled;
    
    public static boolean isMagicEnabled;
    public static boolean manaRenderType;
    
    /* 就是不让你改 */
    private static boolean isColoreggEnabled;
    public static int enchantmentFireBurnId;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        this.loadConfig();
        config.save();
    }

    public static void loadConfig()
    {
        LogLoader.logger().info("Started loading config.");

        String category;
        
        category = "energy";
        isEnergyEnabled = config
                .get(category, "isEnergyEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isEnergyEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isEnergyEnabled").getBoolean();
        
        category = "magnetism";
        isMagnetismEnabled = config
                .get(category, "isMagnetismEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isMagnetismEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isMagnetismEnabled").getBoolean();
        
        category = "machine";
        isMachineEnabled = config
                .get(category, "isMachineEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isMachineEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isMachineEnabled").getBoolean();
        
        category = "computer";
        isComputerEnabled = config
                .get(category, "isComputerEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isComputerEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isComputerEnabled").getBoolean();
        
        category = "science";
        isScienceEnabled = config
                .get(category, "isScienceEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isScienceEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isScienceEnabled").getBoolean();
        
        category = "furniture";
        isFurnitureEnabled = config
                .get(category, "isFurnitureEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isFurnitureEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isFurnitureEnabled").getBoolean();
        
        category = "food";
        isFoodEnabled = config
                .get(category, "isFoodEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isFoodEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isFoodEnabled").getBoolean();
        
        category = "flan";
        isFlanEnabled = config
                .get(category, "isFlanEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isFlanEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isFlanEnabled").getBoolean();
        
        category = "magic";
        isMagicEnabled = config
                .get(category, "isMagicEnabled", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".isMagicEnabled.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".isMagicEnabled").getBoolean();
        manaRenderType = config
                .get(category, "manaRenderType", true,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".manaRenderType.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".manaRenderType").getBoolean();
        
        category = "coloregg";
        String pw = config
                .get(category, "gerKingPassword", "guess password!",
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".gerKingPassword.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".gerKingPassword").getString();
        if (pw.equals(PASSWORD))
        {
            isColoreggEnabled = true;
            LogLoader.logger().info(StatCollector.translateToLocal("console.coloregg.enable"));
        }
        enchantmentFireBurnId = config
                .get(category, "enchantmentFireBurnId", 36,
                        StatCollector.translateToLocal(dawncraft.MODID + ".config." + category + ".enchantmentFireBurnId.tooltip"))
                .setLanguageKey(dawncraft.MODID + ".config." + category + ".enchantmentFireBurnId").getInt();
        
        for (String category2 : config.getCategoryNames())
        {
            config.setCategoryLanguageKey(category2, dawncraft.MODID + ".config." + category2);
            config.setCategoryComment(category2, StatCollector.translateToLocal(dawncraft.MODID + ".config." + category2 + ".tooltip"));
        }

        LogLoader.logger().info("Finished loading config.");
    }

    public static Configuration config()
    {
        return config;
    }

    public static boolean isColoreggEnabled()
    {
        return isColoreggEnabled;
    }
}
