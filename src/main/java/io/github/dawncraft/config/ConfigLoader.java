package io.github.dawncraft.config;

import io.github.dawncraft.Dawncraft;
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
    public static final String PASSWORD = String.valueOf(0x459b7d);

    private static Configuration config;

    public static boolean isEnergyEnabled;

    public static boolean isMagnetismEnabled;

    public static boolean isMachineEnabled;

    public static boolean isComputerEnabled;

    public static boolean isScienceEnabled;

    public static boolean isFurnitureEnabled;

    public static boolean isFoodEnabled;

    public static boolean isGunEnabled;

    public static boolean isMagicEnabled;
    public static boolean manaRenderType;
    public static int publicPrepare;
    public static int publicCooldown;

    /* 就是不让你改 */
    private static boolean isColoreggEnabled;
    public static int enchantmentFireBurnId;
    
    public ConfigLoader(FMLPreInitializationEvent event)
    {
        // TODO 好吧我又想重写配置了
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
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isEnergyEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isEnergyEnabled").getBoolean();

        category = "magnetism";
        isMagnetismEnabled = config
                .get(category, "isMagnetismEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isMagnetismEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isMagnetismEnabled").getBoolean();

        category = "machine";
        isMachineEnabled = config
                .get(category, "isMachineEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isMachineEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isMachineEnabled").getBoolean();

        category = "computer";
        isComputerEnabled = config
                .get(category, "isComputerEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isComputerEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isComputerEnabled").getBoolean();

        category = "science";
        isScienceEnabled = config
                .get(category, "isScienceEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isScienceEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isScienceEnabled").getBoolean();

        category = "furniture";
        isFurnitureEnabled = config
                .get(category, "isFurnitureEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isFurnitureEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isFurnitureEnabled").getBoolean();

        category = "food";
        isFoodEnabled = config
                .get(category, "isFoodEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isFoodEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isFoodEnabled").getBoolean();

        category = "gun";
        isGunEnabled = config
                .get(category, "isGunEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isGunEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isGunEnabled").getBoolean();

        category = "magic";
        isMagicEnabled = config
                .get(category, "isMagicEnabled", true,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".isMagicEnabled.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".isMagicEnabled").getBoolean();
        manaRenderType = config
                .get(category, "manaRenderType", false,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".manaRenderType.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".manaRenderType").getBoolean();
        publicPrepare = config
                .get(category, "publicPrepareTicks", 20,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".publicPrepareTicks.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".publicPrepareTicks").getInt();
        publicCooldown = config
                .get(category, "publicCooldownTicks", 20,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".publicCooldownTicks.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".publicCooldownTicks").getInt();

        category = "coloregg";
        String pw = config
                .get(category, "gerKingPassword", "guess password!",
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".gerKingPassword.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".gerKingPassword").getString();
        if (pw.equals(PASSWORD))
        {
            isColoreggEnabled = true;
            LogLoader.logger().info(StatCollector.translateToLocal("console.coloregg.enable"));
        }
        enchantmentFireBurnId = config
                .get(category, "enchantmentFireBurnId", 36,
                        StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category + ".enchantmentFireBurnId.tooltip"))
                .setLanguageKey(Dawncraft.MODID + ".config." + category + ".enchantmentFireBurnId").getInt();

        for (String category2 : config.getCategoryNames())
        {
            config.setCategoryLanguageKey(category2, Dawncraft.MODID + ".config." + category2);
            config.setCategoryComment(category2, StatCollector.translateToLocal(Dawncraft.MODID + ".config." + category2 + ".tooltip"));
        }
        
        config.save();

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
