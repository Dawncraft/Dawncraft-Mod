package io.github.dawncraft.world;

import io.github.dawncraft.Dawncraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.ValueType;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class WorldEventHandler
{
    @SubscribeEvent
    public static void onWorldLoaded(WorldEvent.Load event)
    {
        // Register custom game rules
        GameRules gamerules = event.getWorld().getGameRules();
        addGameRule(gamerules, "naturalRecovery", String.valueOf(true), ValueType.BOOLEAN_VALUE);
        addGameRule(gamerules, "skillCooldown", String.valueOf(true), ValueType.BOOLEAN_VALUE);
    }

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event)
    {
        if (event.getDimension() == WorldInit.DAWNWORLD.getId())
        {
            if (event.getEntity() instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.getEntity();
                // entityPlayerMP.triggerAchievement(AchievementLoader.dawnArrival);
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
