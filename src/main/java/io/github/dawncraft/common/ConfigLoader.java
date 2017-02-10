package io.github.dawncraft.common;

import org.apache.logging.log4j.Logger;

import io.github.dawncraft.dawncraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register a logger and load configs.
 * 
 * @author QingChenW
 */
public class ConfigLoader
{
    private static Logger logger;
    private static Configuration config;
    
    public static String energy = "energy";

    public static String magic = "magic";
    public static boolean manaRenderType;
    
    public static String coloregg = "coloregg";
    public static int enchantmentFireBurnId;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        this.loadConfig();
    }

    public static void loadConfig()
    {
        logger.info("Started loading config. ");
        String comment;

        comment = "Set type of mana Render.True is 'Terraria' or false is 'Hearthstone'.Idea from 单大帅.Thanks. ";
        ConfigLoader.manaRenderType = config.get(magic, "manaRenderType", true, comment).setLanguageKey(dawncraft.MODID + ".configgui.manaRenderType").getBoolean(true);
        
        comment = "Enchantment id. ";
        enchantmentFireBurnId = config().get(coloregg, "enchantmentFireBurnId", 36, comment).setLanguageKey(dawncraft.MODID + ".configgui.enchantmentFireBurnId").getInt();
        
        for(String category : ConfigLoader.config.getCategoryNames())
        {
            ConfigLoader.config.setCategoryLanguageKey(category, dawncraft.MODID + ".configgui.category." + category);
        }
        
        config.save();
        logger.info("Finished loading config. ");
    }

    public static Logger logger()
    {
        return logger;
    }
    
    public static Configuration config()
    {
        return config;
    }
}
