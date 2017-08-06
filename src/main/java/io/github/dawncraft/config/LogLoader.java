package io.github.dawncraft.config;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register a logger and log something.
 *
 * @author QingChenW
 */
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
