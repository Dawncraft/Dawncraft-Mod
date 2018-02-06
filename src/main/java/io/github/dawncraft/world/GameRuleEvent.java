package io.github.dawncraft.world;

import net.minecraft.world.GameRules;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GameRuleEvent
{
    public GameRuleEvent(FMLInitializationEvent event) {}

    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event)
    {
        GameRules gamerules = event.world.getGameRules();
    }

    public static void addGameRule(GameRules gamerules, String key, String value, GameRules.ValueType type)
    {
        if(!gamerules.hasRule(key))
        {
            gamerules.addGameRule(key, value, type);
        }
    }
}
