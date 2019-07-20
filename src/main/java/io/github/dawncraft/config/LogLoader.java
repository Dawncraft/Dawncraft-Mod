package io.github.dawncraft.config;

import org.apache.logging.log4j.Logger;

/**
 * Register a logger and log something.
 *
 * @author QingChenW
 */
public class LogLoader
{
    private static Logger logger;

    public static void init(Logger logger)
    {
        LogLoader.logger = logger;
        logger.info("Load logger successfully.");
    }

    public static Logger logger()
    {
        return logger;
    }
}
