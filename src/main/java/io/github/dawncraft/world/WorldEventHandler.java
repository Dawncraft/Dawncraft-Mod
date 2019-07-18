package io.github.dawncraft.world;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldServer;
import net.minecraft.world.GameRules.ValueType;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.stats.AchievementLoader;

public class WorldEventHandler
{
    public WorldEventHandler() {}

    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event)
    {
        // Register custom game rules
        GameRules gamerules = event.world.getGameRules();
        addGameRule(gamerules, "naturalRecovery", String.valueOf(true), ValueType.BOOLEAN_VALUE);
        addGameRule(gamerules, "skillCooldown", String.valueOf(true), ValueType.BOOLEAN_VALUE);
        
        // Register custom world teleporter by reflection
        if (!event.world.isRemote && event.world.provider.getDimensionId() == WorldLoader.DAWNWORLD)
        {
            WorldServer worldServer = (WorldServer) event.world;
            try
            {
                Field field = ReflectionHelper.findField(WorldServer.class, "worldTeleporter", "field_85177_Q");
                EnumHelper.setFailsafeFieldValue(field, worldServer, new TeleporterDawn(worldServer));
            }
            catch (Exception e)
            {
                LogLoader.logger().error("Reflect world teleporter failed: {}", e.toString());
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityTravelToDimension(EntityTravelToDimensionEvent event)
    {
        if (event.dimension == WorldLoader.DAWNWORLD)
        {
            if (event.entity instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.entity;
                entityPlayerMP.triggerAchievement(AchievementLoader.dawnArrival);
            }
        }
    }

    public static void addGameRule(GameRules gamerules, String key, String value, ValueType type)
    {
        if (!gamerules.hasRule(key))
        {
            gamerules.addGameRule(key, value, type);
        }
    }
}
