package io.github.dawncraft.world;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.ValueType;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GameRuleEvent
{
    public GameRuleEvent(FMLInitializationEvent event)
    {
    }
    
    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event)
    {
        GameRules gamerules = event.world.getGameRules();
        addGameRule(gamerules, "naturalRecovery", String.valueOf(true), ValueType.BOOLEAN_VALUE);
    }
    
    public static void addGameRule(GameRules gamerules, String key, String value, ValueType type)
    {
        if(!gamerules.hasRule(key))
        {
            gamerules.addGameRule(key, value, type);
        }
    }
}
