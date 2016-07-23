package com.github.wdawning.dawncraft.common;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader
{
	private static Logger logger;
	private static Configuration config;

    public static int potionGerPowerID;
    public static int potionBadGerID;
    public static int potionBrainDeadID;
    
    public static int enchantmentFireBurnId;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
        
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        registerConfig();

        config.save();
    }

    private static void registerConfig()
    {
    	logger.info("Started loading config. ");
    	String comment;
    	
    	comment = "Fire burn enchantment id. ";
        enchantmentFireBurnId = config.get(Configuration.CATEGORY_GENERAL, "enchantmentFireBurnId", 36, comment).getInt();
        
    	comment = "Potion id. ";
        potionGerPowerID = config.get(Configuration.CATEGORY_GENERAL, "potionGerPowerID", 32, comment).getInt();
        potionBadGerID = config.get(Configuration.CATEGORY_GENERAL, "potionBadGerID", 33, comment).getInt();
        potionBrainDeadID = config.get(Configuration.CATEGORY_GENERAL, "potionBrainDeadID", 34, comment).getInt();
        
        logger.info("Finished loading config. ");
    }
    
    public static Logger logger()
    {
        return logger;
    }
}