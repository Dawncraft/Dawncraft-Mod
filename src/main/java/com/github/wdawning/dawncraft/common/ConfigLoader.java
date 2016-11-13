package com.github.wdawning.dawncraft.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.github.wdawning.dawncraft.dawncraft;

public class ConfigLoader
{
	private static Logger logger;
	private static Configuration config;
	
    public static String category = dawncraft.MODID;
	
    public static boolean manaRenderType;

    public static int potionGerPowerID;
    public static int potionBadGerID;
    public static int potionBrainDeadID;
    
    public static int enchantmentFireBurnId;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	config = new Configuration(event.getSuggestedConfigurationFile());
        config().load();
        registerConfig();
    }

    public static void registerConfig()
    {
    	logger.info("Started loading config. ");
    	String comment;
    	
    	comment = "Set type of mana Render.True is 'Terraria' or false is 'Hearthstone'.Idea from 单大帅.Thanks. ";
        ConfigLoader.manaRenderType = config().get(category, "manaRenderType", true, comment).setLanguageKey(dawncraft.MODID + ".configgui.manaRenderType").getBoolean(true);
    	comment = "Enchantment id. ";
        enchantmentFireBurnId = config().get(category, "enchantmentFireBurnId", 36, comment).setLanguageKey(dawncraft.MODID + ".configgui.enchantmentFireBurnId").getInt();
        
    	comment = "Potion id. ";
        potionGerPowerID = config().get(category, "potionGerPowerID", 32, comment).setLanguageKey(dawncraft.MODID + ".configgui.potionGerPowerID").getInt();
        potionBadGerID = config().get(category, "potionBadGerID", 33, comment).setLanguageKey(dawncraft.MODID + ".configgui.potionBadGerID").getInt();
        potionBrainDeadID = config().get(category, "potionBrainDeadID", 34, comment).setLanguageKey(dawncraft.MODID + ".configgui.potionBrainDeadID").getInt();
        
        for (String category : ConfigLoader.config.getCategoryNames())
        {
            ConfigLoader.config.setCategoryLanguageKey(category, dawncraft.MODID + ".configgui.category." + category);
        }
        
        config().save();
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