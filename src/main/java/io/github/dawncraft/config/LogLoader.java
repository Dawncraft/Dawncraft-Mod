package io.github.dawncraft.config;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class LogLoader
{
    private static Logger logger;
    
    public LogLoader(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info("Logger loaded successfully.");
    }
    
    public static Logger logger()
    {
        return logger;
    }
}
