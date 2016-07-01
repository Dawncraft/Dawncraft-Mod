package com.github.wdawning.dawncraft.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader
{
	private static Configuration config;

    public static int potionGerPowerID;
    public static int potionBadGerID;
    public static int potionBrainDeadID;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        registerConfig();

        config.save();
    }

    private static void registerConfig()
    {
        potionGerPowerID = config.get(Configuration.CATEGORY_GENERAL, "potionGerPowerID", 32).getInt();
        potionBadGerID = config.get(Configuration.CATEGORY_GENERAL, "potionBadGerID", 33).getInt();
        potionBrainDeadID = config.get(Configuration.CATEGORY_GENERAL, "potionBrainDeadID", 34).getInt();
    }
}