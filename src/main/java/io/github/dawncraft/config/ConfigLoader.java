package io.github.dawncraft.config;

import java.io.File;

import io.github.dawncraft.Dawncraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Register a configuration manager and some configurations.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
@Config(modid = Dawncraft.MODID, name = Dawncraft.NAME, category = "")
@Config.LangKey("config." + Dawncraft.MODID + ".general")
public class ConfigLoader
{
    @Config.Ignore
    private static Configuration config;

    @Config.Comment("")
    @Config.LangKey("config." + Dawncraft.MODID + ".furniture.chairHealAmount")
    @Config.RangeInt(min = 0)
    public static int chairHealAmount = 0;

    @Config.Comment("")
    @Config.LangKey("config." + Dawncraft.MODID + ".cuisine.isThirstEnabled")
    public static boolean isThirstEnabled = false;

    @Config.Comment("")
    @Config.LangKey("config." + Dawncraft.MODID + ".magic.globalPrepareTicks")
    @Config.RangeInt(min = 0)
    public static int globalPrepareTicks = 10;
    @Config.Comment("")
    @Config.LangKey("config." + Dawncraft.MODID + ".magic.globalCooldownTick")
    @Config.RangeInt(min = 0)
    public static int globalCooldownTick = 20;

    @Config.Comment("")
    @Config.LangKey("config." + Dawncraft.MODID + ".colorEgg.gerKingPassword")
    @Config.RequiresMcRestart
    public static String gerKingPassword = "Please guess password!";
    @Config.Comment("")
    @Config.LangKey("config." + Dawncraft.MODID + ".colorEgg.rangeToCheck")
    @Config.RangeDouble(min = 0.0D)
    public static double rangeToCheck = 32.0D;

    public static void init(File file)
    {
        config = new Configuration(file);
        config.load();
        LogLoader.logger().info("Load configuration successfully.");
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Dawncraft.MODID))
        {
            ConfigManager.sync(Dawncraft.MODID, Config.Type.INSTANCE);
        }
    }

    public static Configuration config()
    {
        return config;
    }

    public static boolean isColoreggEnabled()
    {
        return gerKingPassword.hashCode() == 474280700;
    }
}
